package com.example.service;

import com.example.domain.Brand;
import com.example.domain.BrandExpand;

public class ComsumerService {

    public void test(){
        Brand brand = new Brand(3L, "飞机");
        BrandExpand brandExpand = new BrandExpand(3L, "沈阳飞机工业集团");
        Long brandId = 3L;
        handle(brandId, (b, be) -> {
            if (brandId == b.getBrandId()) {
                b.setBrandName(brand.getBrandName());
            }
        });

    }

    private void handle(Long brandId, Comsumer<Brand, BrandExpand> comsumer) {
        // ... 根据 brandId 生成 Brand, BrandExpand
        Brand brand = new Brand();
        BrandExpand brandExpand = new BrandExpand();
        comsumer.apply(brand, brandExpand);
    }

    private static interface Comsumer<A, B>{
        void apply(A a, B b);
    }
}
