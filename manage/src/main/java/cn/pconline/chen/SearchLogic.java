package cn.pconline.chen;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchLogic {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private String searchDir = "E:\\Test\\Index";
    private static File indexFile = null;
    private static IndexSearcher searcher = null;
    private static Analyzer analyzer = null;
    /** ����ҳ�滺�� */
    private int maxBufferedDocs = 500;

    public static void main(String[] args) {
        SearchLogic logic = new SearchLogic();
        try {
            Long startTime = System.currentTimeMillis();
            List<SearchBean> result = logic.getResult("��Ů");
            int i = 0;
            for(SearchBean bean : result) {
                if(i == 10)
                    break;
                System.out.println("bean.className " + bean.getClass().getName() + " : bean.id " + bean.getId()+ " : bean.name " + bean.getName());
                i++;
            }

            System.out.println("searchBeans.size : " + result.size());
            Long endTime = System.currentTimeMillis();
            System.out.println("��ѯ�����ѵ�ʱ��Ϊ��" + (endTime-startTime)/1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    /**
     * ��ȡ���ݿ�����
     * @return ResultSet
     * @throws Exception
     */
    public List<SearchBean> getResult(String queryStr) throws Exception {
        List<SearchBean> result = null;
        conn = JdbcUtil.getConnection();
        if(conn == null) {
            throw new Exception("���ݿ�����ʧ�ܣ�");
        }
        String sql = "SELECT dl_client_master_spider_id, name FROM dl_client_master_spider";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            this.createIndex(rs);   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø�������
            TopDocs topDocs = this.search(queryStr);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            result = this.addHits2List(scoreDocs);
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("���ݿ��ѯsql���� sql : " + sql);
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return result;
    }

    /**
     * ��������
     * @param queryStr
     * @return
     * @throws Exception
     */
    private TopDocs search(String queryStr) throws Exception {
        if(searcher == null) {
            indexFile = new File(searchDir);
            searcher = new IndexSearcher(FSDirectory.open(indexFile));
        }
        searcher.setSimilarity(Similarity.getDefault());
//        searcher.setSimilarity(new IKSimilarity());
        QueryParser parser = new QueryParser(Version.LUCENE_30,"m_name",new IKAnalyzer());
        Query query = parser.parse(queryStr);

        TopDocs topDocs = searcher.search(query, searcher.maxDoc());
        return topDocs;
    }

    /**
     * ���ؽ������ӵ�List��
     * @param scoreDocs
     * @return
     * @throws Exception
     */
    private List<SearchBean> addHits2List(ScoreDoc[] scoreDocs ) throws Exception {
        List<SearchBean> listBean = new ArrayList<>();
        SearchBean bean = null;
        for(int i=0 ; i<scoreDocs.length; i++) {
            int docId = scoreDocs[i].doc;
            Document doc = searcher.doc(docId);
            bean = new SearchBean();
            bean.setId(doc.get("m_id"));
            bean.setName(doc.get("m_name"));
            listBean.add(bean);
        }
        return listBean;
    }


    /**
     * Ϊ���ݿ�������ݴ�������
     * @param rs
     * @throws Exception
     */
    private void createIndex(ResultSet rs) throws Exception {
        Directory directory = null;
        IndexWriter indexWriter = null;

        try {
            indexFile = new File(searchDir);
            if(!indexFile.exists()) {
                indexFile.mkdir();
            }
            directory = FSDirectory.open(indexFile);
            analyzer = new IKAnalyzer();

            indexWriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
            indexWriter.setMaxBufferedDocs(maxBufferedDocs);
            Document doc = null;
            while(rs.next()) {
                doc = new Document();
                Field id = new Field("m_id", String.valueOf(rs.getInt("dl_client_master_spider_id")), Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.NO);
                Field name = new Field("m_name", rs.getString("name") == null ? "" : rs.getString("name"), Field.Store.YES,Field.Index.ANALYZED, Field.TermVector.NO);
                Field intro = new Field("m_intro", rs.getString("intro") == null ? "" : rs.getString("intro"), Field.Store.YES,Field.Index.ANALYZED, Field.TermVector.NO);
                doc.add(id);
                doc.add(name);
                doc.add(intro);
                indexWriter.addDocument(doc);
            }

            indexWriter.optimize();
            indexWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
