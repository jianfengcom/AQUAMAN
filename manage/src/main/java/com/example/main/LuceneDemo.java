package com.example.main;

import com.example.domain.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

// 参考(Lucene 入门程序): https://www.jianshu.com/p/35c9301bd24f
public class LuceneDemo {
    public static void main(String[] args) throws Exception {
        new LuceneDemo().findIndexDB();
    }

    // 创建索引库
    private void createIndexDB() throws IOException {
        User user = new User("9527", "陈hl", "三年二班班主任");

        // 创建Document对象【Lucene包下】
        Document document = new Document();

        // 将JavaBean对象所有的属性值，均放到Document对象中去

        /**
         * 向Document对象加入一个字段
         * 参数一：字段的关键字
         * 参数二：字符的值
         * 参数三：是否要存储到原始记录表中
         *      YES表示是
         *      NO表示否
         * 参数四：是否需要将存储的数据拆分到词汇表中
         *      ANALYZED表示拆分
         *      NOT_ANALYZED表示不拆分
         *
         * */

        document.add(new Field("id", user.getId(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("userName", user.getUserName(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("sal", user.getSal(), Field.Store.YES, Field.Index.ANALYZED));

        // 创建 IndexWriter对象
        Directory directory = FSDirectory.open(new File("H:/createIndexDB"));

        // 使用标准的分词算法对原始记录表进行拆分
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);

        // LIMITED默认是1W个
        IndexWriter.MaxFieldLength maxFieldLength = IndexWriter.MaxFieldLength.LIMITED;
        /**
         * IndexWriter将我们的document对象写到硬盘中
         *
         * 参数一：Directory d,写到硬盘中的目录路径是什么
         * 参数二：Analyzer a, 以何种算法来对document中的原始记录表数据进行拆分成词汇表
         * 参数三：MaxFieldLength mfl 最多将文本拆分出多少个词汇
         *
         * */
        IndexWriter indexWriter = new IndexWriter(directory, analyzer, maxFieldLength);

        // 将Document对象通过IndexWriter对象写入索引库中
        indexWriter.addDocument(document);

        // 关闭IndexWriter
        indexWriter.close();
    }

    // 根据关键字查询索引库中的内容
    private void findIndexDB() throws Exception {
        /**
         * 参数一： IndexSearcher(Directory path)查询 xx目录的索引库
         * */
        Directory directory = FSDirectory.open(new File("H:/createIndexDB"));

        // 创建IndexSearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(directory);

        // 创建QueryParser对象
        /**
         * 参数一： Version matchVersion 版本号【和上面是一样的】
         * 参数二：String f【要查询的字段】
         * 参数三：Analyzer a【使用的拆词算法】
         * */
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        QueryParser queryParser = new QueryParser(Version.LUCENE_30, "userName", analyzer);

        // 给出要查询的关键字
        String keyWords = "陈";

        // 创建Query对象来封装关键字
        Query query = queryParser.parse(keyWords);

        // 用IndexSearcher对象去索引库中查询符合条件的前100条记录，不足100条记录的以实际为准
        TopDocs topDocs = indexSearcher.search(query, 100);

        // 获取符合条件的编号
        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
            int no = scoreDoc.doc;
            // 用indexSearcher对象去索引库中查询编号对应的Document对象
            Document document = indexSearcher.doc(no);

            // 将Document对象中的所有属性取出，再封装回JavaBean对象中
            String id = document.get("id");
            String userName = document.get("userName");
            String sal = document.get("sal");

            User user = new User(id, userName, sal);
            System.out.println(user);
        }
    }
}
