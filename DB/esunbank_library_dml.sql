-- 使用者資料表
INSERT INTO USERS (USER_ID,PHONE_NUMBER,PASSWORD,USER_NAME,REGISTRATION_TIME,LAST_LOGIN) VALUES (USERS_SEQ.NEXTVAL,'0987654321','123','Joy',TO_TIMESTAMP('2018-03-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2024-02-02 00:00:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO USERS (USER_ID,PHONE_NUMBER,PASSWORD,USER_NAME,REGISTRATION_TIME,LAST_LOGIN) VALUES (USERS_SEQ.NEXTVAL,'0987654322','123','Joe',TO_TIMESTAMP('2018-05-05 00:00:00','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2024-02-01 13:50:00','YYYY-MM-DD HH24:MI:SS'));

-- 庫存資料表
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (1,'978-1-234567-89-0',TO_TIMESTAMP('2017-10-06 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (2,'978-3-456789-12-3',TO_TIMESTAMP('2017-11-16 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (3,'978-9-876543-21-0',TO_TIMESTAMP('2018-02-15 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (4,'978-0-987654-32-1',TO_TIMESTAMP('2018-06-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (5,'978-5-432109-87-6',TO_TIMESTAMP('2018-06-30 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (6,'978-2-109876-54-3',TO_TIMESTAMP('2018-08-13 00:00:00','YYYY-MM-DD HH24:MI:SS'),'1');
INSERT INTO INVENTORY (INVENTORY_ID,ISBN,STORE_TIME,STATUS) VALUES (7,'978-3-456789-01-2',TO_TIMESTAMP('2019-05-20 12:30:00','YYYY-MM-DD HH24:MI:SS'),'1');

-- 借閱紀錄資料表


-- 書籍資料表
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-1-234567-89-0','時光之影','張雅琪','一段跨越世紀的愛情，時光中蘊藏的秘密令人動容');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-3-456789-12-3','星夜奏鳴曲','林宇辰','音樂與愛情的奇幻故事，星空下的悅耳旋律，串起心靈共鳴');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-9-876543-21-0','追尋失落的記憶','陳冠宇','一名失憶男子的冒險，解開過去謎團，尋回失落的記憶');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-0-987654-32-1','風中之舞','蔡欣婷','舞者的靈魂，風中搖曳的優雅，融入音樂中的翩翩起舞');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-5-432109-87-6','未知的彼岸','王宇凡','冒險者穿越異世界，面對未知的挑戰，尋找彼岸的奇蹟');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-2-109876-54-3','紙上奇幻城','林曉彤','書中世界的冒險，文字與想像共舞，打開奇幻之門');
INSERT INTO BOOK (ISBN,NAME,AUTHOR,INTRODUCTION) VALUES ('978-3-456789-01-2','Java','Joe','穿梭於星際之間的奇幻冒險，探索未知的宇宙奧秘');

COMMIT;