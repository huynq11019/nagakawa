-- ----------------------------
-- Comment on file_entry
-- ----------------------------
COMMENT ON COLUMN "file_entry"."id" IS 'Bảng lưu thông tin file upload lên hệ thống';
COMMENT ON COLUMN "file_entry"."class_name" IS 'Tên entity sử dụng file';
COMMENT ON COLUMN "file_entry"."class_pk" IS 'entityId sử dụng file';
COMMENT ON COLUMN "file_entry"."content_type" IS 'MIME type của file, e.g image/gif';
COMMENT ON COLUMN "file_entry"."name" IS 'Tên của file sau khi upload lên hệ thống, có thể đc mã hóa';
COMMENT ON COLUMN "file_entry"."original_name" IS 'Tên gốc của file';
COMMENT ON COLUMN "file_entry"."size" IS 'Kích thước file';
COMMENT ON COLUMN "file_entry"."folder_id" IS 'Id folder lưu file';
-- ----------------------------
-- Comment on folder_entry
-- ----------------------------
COMMENT ON COLUMN "folder_entry"."id" IS 'Bảng lưu thông tin folder chứa file upload';
COMMENT ON COLUMN "folder_entry"."file_count" IS 'Số file trong folder, nếu đạt tối đa thì cần tạo thư mục mới';
COMMENT ON COLUMN "folder_entry"."name" IS 'Tên thư mục';
COMMENT ON COLUMN "folder_entry"."parent_folder_id" IS 'Thư mục cha';
-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO "privilege" VALUES (1, 'Quyền vào chức năng Quản lý người dùng', 'VIEW_USER');
INSERT INTO "privilege" VALUES (2, 'Quyền thêm mới người dùng', 'ADD_USER');
INSERT INTO "privilege" VALUES (3, 'Quyền cập nhật thông tin người dùng', 'EDIT_USER');
INSERT INTO "privilege" VALUES (4, 'Quyền xóa người dùng', 'DELETE_USER');
 -- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "role"("id", "created_by", "created_date", "last_modified_by", "last_modified_date", "description", "immutable", "level", "assignable", "name", "status") VALUES (1, 'superadmin', '2021-06-23 10:38:32', '2021-06-23 10:38:32', '2021-06-23 10:38:32', 'Super Aministrator', 1, 0, 0, 'ROLE_SUPER_ADMIN', 1);
INSERT INTO "role"("id", "created_by", "created_date", "last_modified_by", "last_modified_date", "description", "immutable", "level", "assignable", "name", "status") VALUES (4, 'superadmin', '2021-06-23 10:38:32', '2021-06-23 10:38:32', '2021-06-23 10:38:32', 'Người dùng hệ thống', 1, 10, 1, 'ROLE_USER', 1);

-- ----------------------------
-- Records of roles_privileges
-- ----------------------------
INSERT INTO "roles_privileges" VALUES (1, 1);
INSERT INTO "roles_privileges" VALUES (1, 2);
INSERT INTO "roles_privileges" VALUES (1, 3);
INSERT INTO "roles_privileges" VALUES (1, 4);
-- ----------------------------
-- Records of user_
-- ----------------------------
INSERT INTO "user_" VALUES (1, '0', NULL, NULL, NULL, NULL, NULL, 'superadmin@email.com', 'Super Admin',
'$2a$11$ly3HQ3E2eryp1jUKX7C77uLveflgxmEDnfFUW0NH4IgnrutSiutRS', NULL, 0, 1, 'superadmin');