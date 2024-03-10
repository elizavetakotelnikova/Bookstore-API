ALTER TABLE order_content
DROP CONSTRAINT order_content_order_id_fkey,
  ADD FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE order_content
DROP CONSTRAINT order_content_product_id_fkey,
  ADD FOREIGN KEY (product_id) REFERENCES products(id);