package com.example.service;

import com.example.domain.Brand;
import com.example.domain.BrandExpand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComsumerService {

    @Test
    public void test(){
        List<BrandExpand> result = new ArrayList<>();

        Brand brand = new Brand(3L, "�ɻ�");
        BrandExpand brandExpand = new BrandExpand(3L, "�����ɻ���ҵ����");

        Long brandId = 3L;
        handle(brandId, (b, be) -> {
            if (brandId == b.getBrandId()) {
                b.setBrandName(brand.getBrandName());
                be.setCompany(brandExpand.getCompany());
            }
            result.add(be);
        });

        result.add(brandExpand);
        // �� brandId ˳��, ����reversed�䵹��
        result.sort(Comparator.comparingLong(BrandExpand::getBrandId).reversed());
        System.out.println(result);
    }

    private void handle(Long brandId, Comsumer<Brand, BrandExpand> comsumer) {
        // ... ���� brandId ���� Brand, BrandExpand
        Brand brand = new Brand();
        brand.setBrandId(3L);

        BrandExpand brandExpand = new BrandExpand();
        brandExpand.setBrandId(7L );
        comsumer.apply(brand, brandExpand);
    }

    private static interface Comsumer<A, B>{
        void apply(A a, B b);
    }
}
