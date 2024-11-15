CREATE DATABASE IF NOT EXISTS Library;
USE Library;
CREATE TABLE IF NOT EXISTS Book (
    ISBN VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    publisher VARCHAR(255),
    publicationDate VARCHAR(20),
    language VARCHAR(50),
    pageNumber INT,
    imageUrl VARCHAR(255),
    description TEXT
);
CREATE TABLE IF NOT EXISTS User (
    userId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50),
    identityNumber VARCHAR(20),
    birth VARCHAR(10),
    gender VARCHAR(6),
    phoneNumber VARCHAR(20),
    email VARCHAR(50),
    address VARCHAR(255)
);
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS USER;


-- Xóa dữ liệu cũ (nếu cần)
-- DELETE FROM User;

-- Reset auto-increment (nếu có)
-- ALTER TABLE User AUTO_INCREMENT = 1;

-- Insert dữ liệu mẫu
INSERT INTO User (userId, name, identityNumber, birth, gender, phoneNumber, email, address)
VALUES
    ('U001', 'Nguyễn Văn An', '123456789', '1990-05-15', 'Nam', '0912345678', 'nguyenvanan@gmail.com', 'Số 123 Đường Lê Lợi, Quận 1, TP.HCM'),

    ('U002', 'Trần Thị Bình', '987654321', '1995-08-22', 'Nữ', '0823456789', 'tranbinhb@gmail.com', '45 Nguyễn Huệ, Quận Hoàn Kiếm, Hà Nội'),

    ('U003', 'Lê Hoàng Cường', '456789123', '1988-12-03', 'Nam', '0934567890', 'lecuong88@gmail.com', '78 Trần Phú, TP Huế'),

    ('U004', 'Phạm Thị Dung', '789123456', '1992-03-30', 'Nữ', '0845678912', 'phamdung92@gmail.com', '256 Nguyễn Trãi, Thanh Xuân, Hà Nội'),

    ('U005', 'Hoàng Văn Em', '321654987', '1985-11-18', 'Nam', '0956789123', 'hoangemp@gmail.com', '89 Lê Duẩn, Đà Nẵng'),

    ('U006', 'Mai Thị Phương', '654987321', '1998-07-25', 'Nữ', '0867891234', 'maiphuong98@gmail.com', '147 Võ Văn Tần, Quận 3, TP.HCM'),

    ('U007', 'Đỗ Văn Giang', '147258369', '1993-09-08', 'Nam', '0978912345', 'dovangiang@gmail.com', '63 Trần Hưng Đạo, Quy Nhơn'),

    ('U008', 'Ngô Thị Hoa', '369258147', '1997-04-12', 'Nữ', '0889123456', 'ngohoa97@gmail.com', '951 Cách Mạng Tháng 8, Quận 10, TP.HCM'),

    ('U009', 'Vũ Đình Ian', '258369147', '1991-01-28', 'Nam', '0890123456', 'vuian91@gmail.com', '357 Điện Biên Phủ, Bình Thạnh, TP.HCM'),

    ('U010', 'Lý Thị Kim', '741852963', '1994-06-17', 'Nữ', '0901234567', 'lykim94@gmail.com', '159 Phan Chu Trinh, Hội An');

-- Thêm một số user trẻ (sinh viên)
INSERT INTO User (userId, name, identityNumber, birth, gender, phoneNumber, email, address)
VALUES
    ('U011', 'Trần Minh Khoa', '963852741', '2000-02-14', 'Nam', '0912345987', 'minhkhoa00@gmail.com', 'KTX Đại học Quốc gia, TP.HCM'),

    ('U012', 'Lê Thị Lan', '852963741', '2001-09-30', 'Nữ', '0923456789', 'lelan01@gmail.com', '42 Sư Vạn Hạnh, Quận 10, TP.HCM'),

    ('U013', 'Phạm Văn Minh', '741963852', '2002-05-20', 'Nam', '0934567891', 'pminh02@gmail.com', 'KTX Khu B, Đại học Cần Thơ');

-- Thêm một số user cao tuổi
INSERT INTO User (userId, name, identityNumber, birth, gender, phoneNumber, email, address)
VALUES
    ('U014', 'Nguyễn Văn Nam', '159753468', '1965-12-25', 'Nam', '0945678912', 'nvnam65@gmail.com', '753 Nguyễn Văn Linh, Quận 7, TP.HCM'),

    ('U015', 'Trần Thị Oanh', '357159468', '1970-08-05', 'Nữ', '0956789123', 'ttoanh70@gmail.com', '159 Lý Thường Kiệt, Hà Nội');