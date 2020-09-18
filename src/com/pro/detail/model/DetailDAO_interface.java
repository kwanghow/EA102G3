package com.pro.detail.model;
import java.sql.Connection;
import java.util.List;

import com.pro.cart.model.Cart;


public interface DetailDAO_interface {
        public void insert(DetailVO DetailVO);
        public void update(DetailVO DetailVO);
        public void delete(String order_id);
        public List<DetailVO> getAll();
        public List<DetailVO> getAllByOrderNo(String order_id);
//        ��q��@�_�[�J
        public void addWithPro_Order(Cart Cart, Connection con);
}
