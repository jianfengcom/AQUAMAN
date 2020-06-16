package com.example.service;

import com.example.domain.Brand;
import com.example.domain.BrandExpand;

public class ComsumerService {

    public void test(){
        Brand brand = new Brand(3L, "�ɻ�");
        BrandExpand brandExpand = new BrandExpand(3L, "�����ɻ���ҵ����");
        Long brandId = 3L;
        handle(brandId, (b, be) -> {
            if (brandId == b.getBrandId()) {
                b.setBrandName(brand.getBrandName());
            }
        });

    }

    private void handle(Long brandId, Comsumer<Brand, BrandExpand> comsumer) {
        // ... ���� brandId ���� Brand, BrandExpand
        Brand brand = new Brand();
        BrandExpand brandExpand = new BrandExpand();
        comsumer.apply(brand, brandExpand);
    }

    private static interface Comsumer<A, B>{
        void apply(A a, B b);
    }
}
