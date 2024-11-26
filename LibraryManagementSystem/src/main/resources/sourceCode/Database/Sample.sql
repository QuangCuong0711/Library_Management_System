USE LIBRARY;
INSERT INTO ADMIN (adminId, name, identityNumber, birth, gender, phoneNumber, email, address, password)
VALUES
    ('admin', 'Admin One', 'ID111111', '1980-02-02', 'Male', '1112223333', 'admin1@example.com', '789 Oak St', 'admin'),
    ('admin1', 'Admin Two', 'ID222222', '1975-03-03', 'Female', '4445556666', 'admin2@example.com', '101 Pine St', 'admin');
INSERT INTO USER (userId, name, identityNumber, birth, gender, phoneNumber, email, address, password)
VALUES ('U001', 'Nguyễn Văn An', '123456789', '1990-05-15', 'Nam', '0912345678',
        'nguyenvanan@gmail.com', 'Số 123 Đường Lê Lợi, Quận 1, TP.HCM', '123456'),

       ('U002', 'Trần Thị Bình', '987654321', '1995-08-22', 'Nữ', '0823456789',
        'tranbinhb@gmail.com', '45 Nguyễn Huệ, Quận Hoàn Kiếm, Hà Nội', '123456'),

       ('U003', 'Lê Hoàng Cường', '456789123', '1988-12-03', 'Nam', '0934567890',
        'lecuong88@gmail.com', '78 Trần Phú, TP Huế', '123456'),

       ('U004', 'Phạm Thị Dung', '789123456', '1992-03-30', 'Nữ', '0845678912',
        'phamdung92@gmail.com', '256 Nguyễn Trãi, Thanh Xuân, Hà Nội', '123456'),

       ('U005', 'Hoàng Văn Em', '321654987', '1985-11-18', 'Nam', '0956789123',
        'hoangemp@gmail.com', '89 Lê Duẩn, Đà Nẵng', '123456'),

       ('U006', 'Mai Thị Phương', '654987321', '1998-07-25', 'Nữ', '0867891234',
        'maiphuong98@gmail.com', '147 Võ Văn Tần, Quận 3, TP.HCM', '123456'),

       ('U007', 'Đỗ Văn Giang', '147258369', '1993-09-08', 'Nam', '0978912345',
        'dovangiang@gmail.com', '63 Trần Hưng Đạo, Quy Nhơn', '123456'),

       ('U008', 'Ngô Thị Hoa', '369258147', '1997-04-12', 'Nữ', '0889123456', 'ngohoa97@gmail.com',
        '951 Cách Mạng Tháng 8, Quận 10, TP.HCM', '123456'),

       ('U009', 'Vũ Đình Ian', '258369147', '1991-01-28', 'Nam', '0890123456', 'vuian91@gmail.com',
        '357 Điện Biên Phủ, Bình Thạnh, TP.HCM', '123456'),

       ('U010', 'Lý Thị Kim', '741852963', '1994-06-17', 'Nữ', '0901234567', 'lykim94@gmail.com',
        '159 Phan Chu Trinh, Hội An', '123456'),

       ('U011', 'Trần Minh Khoa', '963852741', '2000-02-14', 'Nam', '0912345987',
        'minhkhoa00@gmail.com', 'KTX Đại học Quốc gia, TP.HCM', '123456'),

       ('U012', 'Lê Thị Lan', '852963741', '2001-09-30', 'Nữ', '0923456789', 'lelan01@gmail.com',
        '42 Sư Vạn Hạnh, Quận 10, TP.HCM', '123456'),

       ('U013', 'Phạm Văn Minh', '741963852', '2002-05-20', 'Nam', '0934567891',
        'pminh02@gmail.com', 'KTX Khu B, Đại học Cần Thơ', '123456'),

       ('U014', 'Nguyễn Văn Nam', '159753468', '1965-12-25', 'Nam', '0945678912',
        'nvnam65@gmail.com', '753 Nguyễn Văn Linh, Quận 7, TP.HCM', '123456'),

       ('U015', 'Trần Thị Oanh', '357159468', '1970-08-05', 'Nữ', '0956789123',
        'ttoanh70@gmail.com', '159 Lý Thường Kiệt, Hà Nội', '123456');
INSERT INTO BOOK (ISBN, title, author, genre, publicationDate, language, pageNumber, imageUrl, description, quantity)
VALUES
    ('978-3-16-148410-0', 'The Art of War', 'Sun Tzu', 'Non-fiction', '2010-01-01', 'English', 200, 'https://example.com/art_of_war.jpg', 'The Art of War is an ancient Chinese military treatise.', 5),
    ('978-1-4028-9462-6', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '1925-04-10', 'English', 180, 'https://example.com/great_gatsby.jpg', 'The Great Gatsby is a novel by American writer F. Scott Fitzgerald.', 5),
    ('978-0-545-01022-1', 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 'Fantasy', '1997-06-26', 'English', 223, 'https://example.com/harry_potter.jpg', 'Harry Potter and the Philosopher\'s Stone is a fantasy novel.', 5),
    ('978-0-7432-7356-5', 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', '1960-07-11', 'English', 281, 'https://example.com/to_kill_a_mockingbird.jpg', 'To Kill a Mockingbird is a novel by Harper Lee.', 5),
    ('978-0-316-76948-8', '1984', 'George Orwell', 'Dystopian', '1949-06-08', 'English', 328, 'https://example.com/1984.jpg', '1984 is a dystopian social science fiction novel and cautionary tale.', 5);
INSERT INTO Feedback (userID, ISBN, comment, rating, date)
VALUES
    ('U001', '978-3-16-148410-0', 'Great book, very informative!', 5, '2024-11-21'),
    ('U002', '978-1-4028-9462-6', 'Not what I expected.', 2, '2024-11-22'),
    ('U003', '978-0-545-01022-1', 'Good read, but a bit lengthy.', 4, '2024-11-23'),
    ('U004', '978-0-7432-7356-5', 'Excellent book, highly recommend!', 5, '2024-11-24'),
    ('U005', '978-0-7432-7356-5', 'Interesting perspective.', 3, '2024-11-25');
INSERT INTO TICKET (userId, ISBN, borrowedDate, returnedDate, quantity)
VALUES
    ('U001', '978-3-16-148410-0', '2024-11-21', null, 1),
    ('U002', '978-1-4028-9462-6', '2024-10-22', '2024-11-22', 1),
    ('U003', '978-0-545-01022-1', '2024-10-23', '2024-11-23', 1),
    ('U004', '978-0-7432-7356-5', '2024-10-24', '2024-11-24', 1),
    ('U005', '978-0-7432-7356-5', '2024-10-25', '2024-11-25', 1);