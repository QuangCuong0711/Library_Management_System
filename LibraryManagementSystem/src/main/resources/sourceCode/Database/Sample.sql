INSERT INTO LIBRARY.USER (userId, name, identityNumber, birth, gender, phoneNumber, email, address)
VALUES ('U001', 'Nguyễn Văn An', '123456789', '1990-05-15', 'Nam', '0912345678',
        'nguyenvanan@gmail.com', 'Số 123 Đường Lê Lợi, Quận 1, TP.HCM'),

       ('U002', 'Trần Thị Bình', '987654321', '1995-08-22', 'Nữ', '0823456789',
        'tranbinhb@gmail.com', '45 Nguyễn Huệ, Quận Hoàn Kiếm, Hà Nội'),

       ('U003', 'Lê Hoàng Cường', '456789123', '1988-12-03', 'Nam', '0934567890',
        'lecuong88@gmail.com', '78 Trần Phú, TP Huế'),

       ('U004', 'Phạm Thị Dung', '789123456', '1992-03-30', 'Nữ', '0845678912',
        'phamdung92@gmail.com', '256 Nguyễn Trãi, Thanh Xuân, Hà Nội'),

       ('U005', 'Hoàng Văn Em', '321654987', '1985-11-18', 'Nam', '0956789123',
        'hoangemp@gmail.com', '89 Lê Duẩn, Đà Nẵng'),

       ('U006', 'Mai Thị Phương', '654987321', '1998-07-25', 'Nữ', '0867891234',
        'maiphuong98@gmail.com', '147 Võ Văn Tần, Quận 3, TP.HCM'),

       ('U007', 'Đỗ Văn Giang', '147258369', '1993-09-08', 'Nam', '0978912345',
        'dovangiang@gmail.com', '63 Trần Hưng Đạo, Quy Nhơn'),

       ('U008', 'Ngô Thị Hoa', '369258147', '1997-04-12', 'Nữ', '0889123456', 'ngohoa97@gmail.com',
        '951 Cách Mạng Tháng 8, Quận 10, TP.HCM'),

       ('U009', 'Vũ Đình Ian', '258369147', '1991-01-28', 'Nam', '0890123456', 'vuian91@gmail.com',
        '357 Điện Biên Phủ, Bình Thạnh, TP.HCM'),

       ('U010', 'Lý Thị Kim', '741852963', '1994-06-17', 'Nữ', '0901234567', 'lykim94@gmail.com',
        '159 Phan Chu Trinh, Hội An'),

       ('U011', 'Trần Minh Khoa', '963852741', '2000-02-14', 'Nam', '0912345987',
        'minhkhoa00@gmail.com', 'KTX Đại học Quốc gia, TP.HCM'),

       ('U012', 'Lê Thị Lan', '852963741', '2001-09-30', 'Nữ', '0923456789', 'lelan01@gmail.com',
        '42 Sư Vạn Hạnh, Quận 10, TP.HCM'),

       ('U013', 'Phạm Văn Minh', '741963852', '2002-05-20', 'Nam', '0934567891',
        'pminh02@gmail.com', 'KTX Khu B, Đại học Cần Thơ'),

       ('U014', 'Nguyễn Văn Nam', '159753468', '1965-12-25', 'Nam', '0945678912',
        'nvnam65@gmail.com', '753 Nguyễn Văn Linh, Quận 7, TP.HCM'),

       ('U015', 'Trần Thị Oanh', '357159468', '1970-08-05', 'Nữ', '0956789123',
        'ttoanh70@gmail.com', '159 Lý Thường Kiệt, Hà Nội');
UPDATE library.admin SET name = 'Đỗ Quang Cường', identityNumber = '026205007140', birth = '2005-11-07', gender = 'Nam', phoneNumber = '0357989017', email = 'doquangcuong0711@gmail.com', address = 'Mỹ Đô - Tân Phong - Bình Xuyên - Vĩnh Phúc' WHERE adminId = 'A001';
UPDATE library.admin SET name = 'Nguyễn Thanh Hải', identityNumber = '012345678987', birth = '2005-07-10', gender = 'Nam', phoneNumber = '0127365472', email = 'nguyenthanhhai@gmail.com', address = null WHERE adminId = 'A002';
UPDATE library.admin SET name = 'Nguyễn Công Mạnh Hùng', identityNumber = '028565848465', birth = '2005-08-22', gender = 'Nam', phoneNumber = '0783546435', email = 'nguyencongmanhhung@gmail.com', address = 'Hà Tĩnh ' WHERE adminId = 'A003';
UPDATE library.adminaccount SET nameAccount = 'admin', passwordAccount = 'cg0711', adminId = 'A001' WHERE adminAccountId = 'AA01';
UPDATE library.adminaccount SET nameAccount = 'admin', passwordAccount = '10072005', adminId = 'A002' WHERE adminAccountId = 'AA02';
UPDATE library.adminaccount SET nameAccount = 'admin', passwordAccount = '23021567', adminId = 'A003' WHERE adminAccountId = 'AA03';books
UPDATE library.books SET title = 'Những thời kỳ biến động của nền kinh tế Việt Nam: Bản chất của vấn đề và giải pháp cho tương lai (Communist Review, Vietnam, No. 792, Oct. 2008)', author = '', genre = '', publisher = 'Tran Tri Dung', publicationDate = '2000-01-01', language = 'vi', pageNumber = 25, imageUrl = 'http://books.google.com/books/content?id=6x7LDXyhAQgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 1 WHERE ISBN = '6x7LDXyhAQgC';
UPDATE library.books SET title = 'Số Liệu Thống Kê Việt Nam Thế Kỷ XX', author = '', genre = 'Vietnam', publisher = '', publicationDate = '2000-01-01', language = 'vi', pageNumber = 1146, imageUrl = 'http://books.google.com/books/content?id=CagUAQAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 5 WHERE ISBN = 'CagUAQAAMAAJ';
UPDATE library.books SET title = 'Kinh te Viet Nam - Thang Tram va Dot Pha', author = 'Phạm Minh Chính', genre = '', publisher = 'Nxb Chính trị quốc gia - Sự thật', publicationDate = '2009-05-29', language = 'vi', pageNumber = 587, imageUrl = 'http://books.google.com/books/content?id=CLA-fjsiR0AC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 8 WHERE ISBN = 'CLA-fjsiR0AC';
UPDATE library.books SET title = 'Kinh tế Việt Nam và Hội nhập (Vietnam Economy Monitor Weekly), Volume 1, Issue 2', author = '', genre = '', publisher = 'Vietnamica Monitor', publicationDate = '2000-01-01', language = 'vi', pageNumber = 40, imageUrl = 'http://books.google.com/books/content?id=dXZ0fKnh5-oC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 3 WHERE ISBN = 'dXZ0fKnh5-oC';
UPDATE library.books SET title = 'KHẢO CHỨNG TIỀN SỬ VIỆT NAM', author = 'Trương Thái Du', genre = 'History', publisher = 'Trương Thái Du', publicationDate = '2024-10-02', language = 'vi', pageNumber = 450, imageUrl = 'http://books.google.com/books/content?id=IycmEQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = 'Based on my researching of Chinese Classical Astronomy and Text, this book is the latter version of the former "A New Approach on Old Issues Of Ancient Vietnamese History" - ISBN 9781310245367. The new name is "Researching of Vietnam Prehistory", ISBN 9781370154548. By accessing and comparing vocabularies in the Sino-Tibetan and Austronesian languages, locating them in genetic maps with archaeological, cultural and bibliographic adjustments, we have figured out the meaning of important terms intimate to ancient Vietnamese history as presented below: 1. Việt 越 and Âu 歐 are homophones in the Han Dynasty and before, serving as transcriptions of Jiang - Zhe words by Chinese characters; originally and literally they mean water or water area, and their derivative sense is Country. They are similar to the word Quốc, which means earth/land/soil, and the derivative meaning of which is Country. Accordingly it can be inferred that Vietnam means Southern Country or Southern Land. 2. Lạc 駱 is equivalent and has a birth process similar to that of Việt and Âu, but belongs to the Tai-Kadai linguistic sphere, which matches the northeast region of the present Tai-Kadai language map. Âu Lạc was formed as a result of the combination of Âu and Lạc. It was also the indigenous name of the kingdom of Nam Việt founded by Triệu Đà. Lạc Vương, therefore, means the king of the country; Lạc Hầu - the Marquess; Lạc Dân - The People of the country; but Lạc Điền should be understood as paddy field, rather than country\'s field. 3. Hung King 雄王 is a word created at a much later time than Lac King 駱王, its element Quân 君 simply refers to the Chief of the nation/tribe whether of Âu Việt or Lạc Việt origins. Hùng King as a term is similar to the Yan and Huang emperors of China, or, in a more specific and understandable comparison, equivalent to the Founding Fathers of America. This book also affirms that Nan Jiao 南交 , Jiao Zhi 交阯 , Xiang Jun 象郡 , Jiu Zhen 九真, and Ri Nan 日南 are united concepts related to Chinese Classical Astronomy. While researching the meaning of Nan Jiao, Jiao Zhi, Xiang Jun, Jiu Zhen, and Rinan by using Chinese ancient astronomy, we accidentally figured out the non-superstitious meaning and how to establish the definitions of River Map 河圖, Lo Shu Square 洛書, Yin Yang 陰陽, Five Elements 五行 and Yijing 易經. According to the author, this might be the first time ever in the history of East Asia that the Chinese mysterious "holy grail" was discovered by using scientific method. Due to the digression of the book\'s subject, the references are rough but they are more plausible and reliable than any illusory story that was put on the establishment of River Map, Lo Shu Square, Yin Yang, Five Elements, and Yijing. The first meaning of Jiao Zhi 交阯 is the southern region located next to Nan Jiao南交. Next, Jiao Zhi 交阯 refers to a southern region sharing the border with the Chinese Empire. Within thousand years of southern expansion by different reigns, Jiaozhi located in Hubei of Western Zhou 西周. Before 239 BC, Jiaozhi located between Hunan and Guizhou. Until Eastern Han 東漢 time, Jiaozhi was officially appeared on the Chinese map as a province Jiaozhibu 交阯部 including Jiaozhi district 交阯郡 and eight other districts. During the reign of Qin 秦, Jiaozhi was Xiangjun 象郡. The Xiangjun region was confirmed as Jiaozhi by meaning and by astronomical pre-observation. Jiuzhen 九真 means "the root of the sun" which is similar to the Equator. Rinan 日南 (Sun south) means the southern region of the sun. In Rinan, the gnomon\'s shadow is always located in the south, which is the Southern hemisphere from Tropic of Capricorn to South Pole. The above terms\' meanings will change the important details of China\'s southern provincial history as well as Vietnam\'s.', quantity = 38 WHERE ISBN = 'IycmEQAAQBAJ';
UPDATE library.books SET title = 'Văn kiện Đảng toàn tập', author = 'Đảng cộng sản Việt Nam', genre = 'Vietnam', publisher = '', publicationDate = '2000-01-01', language = 'vi', pageNumber = 688, imageUrl = 'http://books.google.com/books/content?id=Jy7buQEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api', description = null, quantity = 13 WHERE ISBN = 'Jy7buQEACAAJ';
UPDATE library.books SET title = 'Boi canh REDD+ Viet Nam: Nguyên nhân, doi tuong và the che', author = 'Pham Thu Thuy', genre = '', publisher = 'CIFOR', publicationDate = '2012-07-06', language = 'vi', pageNumber = 93, imageUrl = 'http://books.google.com/books/content?id=lfithY6Xl4cC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = 'The context of REDD+ in Vietnam Drivers, agents and institutions', quantity = 9 WHERE ISBN = 'lfithY6Xl4cC';
UPDATE library.books SET title = 'Vietnam Economy Monitor Weekly (Volume 1, Issue 1): Kinh tế Việt Nam và Hội nhập', author = '', genre = '', publisher = 'Vietnamica Monitor', publicationDate = '2000-01-01', language = 'vi', pageNumber = 26, imageUrl = 'http://books.google.com/books/content?id=TCBjTji-fa8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 5 WHERE ISBN = 'TCBjTji-fa8C';
UPDATE library.books SET title = 'Công báo Việt Nam Cộng Hoà, ấn bản Quốc hội (Hạ Nghị Viện).', author = 'Vietnam', genre = 'Vietnam', publisher = '', publicationDate = '2000-01-01', language = 'vi', pageNumber = 520, imageUrl = 'http://books.google.com/books/content?id=tJw7AAAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', description = null, quantity = 25 WHERE ISBN = 'tJw7AAAAMAAJ';
UPDATE library.books SET title = '越南史略', author = 'Trọng Kim Trần', genre = 'Vietnam', publisher = '', publicationDate = '2000-01-01', language = 'vi', pageNumber = 0, imageUrl = '', description = null, quantity = 30 WHERE ISBN = 'uk6O0AEACAAJ';
UPDATE library.useraccount SET nameAccount = 'user', passwordAccount = '1234567', userId = 'U001' WHERE userAccountId = 'UA01';
UPDATE library.useraccount SET nameAccount = 'user', passwordAccount = '1234567', userId = 'U002' WHERE userAccountId = 'UA02';
