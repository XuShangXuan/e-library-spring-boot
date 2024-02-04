package com.esunbank.e_library_spring_boot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esunbank.e_library_spring_boot.dao.BookDataDao;
import com.esunbank.e_library_spring_boot.dao.BorrowingRecordDataDao;
import com.esunbank.e_library_spring_boot.dao.BorrowingRecordInfoDao;
import com.esunbank.e_library_spring_boot.dao.InventoryInfoDao;
import com.esunbank.e_library_spring_boot.dao.UsersInfoDao;
import com.esunbank.e_library_spring_boot.entity.BookInfoMapping;
import com.esunbank.e_library_spring_boot.entity.BorrowBookInfoMapping;
import com.esunbank.e_library_spring_boot.entity.BorrowingRecord;
import com.esunbank.e_library_spring_boot.entity.BorrowingRecordID;
import com.esunbank.e_library_spring_boot.entity.Inventory;
import com.esunbank.e_library_spring_boot.entity.Users;
import com.esunbank.e_library_spring_boot.tool.Page;
import com.esunbank.e_library_spring_boot.vo.BookDataCondition;
import com.esunbank.e_library_spring_boot.vo.BookDataInfo;
import com.esunbank.e_library_spring_boot.vo.BorrowBookDataInfo;
import com.esunbank.e_library_spring_boot.vo.BorrowBookVo;
import com.esunbank.e_library_spring_boot.vo.GenericPageable;
import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;

@Service
public class FrontEndService {
	
	private static Logger logger = LoggerFactory.getLogger(FrontEndService.class);
	
	@Autowired
	private BookDataDao bookDataDao;
	
	@Autowired
	private InventoryInfoDao inventoryInfoDao;
	
	@Autowired
	private UsersInfoDao usersInfoDao;
	
	@Autowired
	private BorrowingRecordInfoDao borrowingRecordInfoDao;
	
	@Autowired
	private BorrowingRecordDataDao borrowingRecordDataDao;
	
	public BookDataInfo queryBooksData(BookDataCondition condition, GenericPageable genericPageable) {
		
		// 資料總筆數
		int dataTotalCount = bookDataDao.queryBookCountByBookDataCondition(condition);
		
		genericPageable = Page.calculatePageable(dataTotalCount, genericPageable);
		
		logger.info("計算後的分頁資訊:" + genericPageable);
		
		// 計算查詢範圍
		int endRowNo = genericPageable.getShowDataCount() * genericPageable.getCurrentPage();
		int startRowNo = endRowNo - genericPageable.getShowDataCount() + 1;
		condition.setEndRowNo(endRowNo);
		condition.setStartRowNo(startRowNo);
		
		logger.info("計算後的條件資訊:" + condition);
		
		List<BookInfoMapping> bookList = bookDataDao.queryBookByBookDataCondition(condition, genericPageable);
		
		BookDataInfo bookDataInfo = BookDataInfo.builder().bookList(bookList).genericPageable(genericPageable).build();
		
		return bookDataInfo;
	}
	
	public BorrowBookDataInfo queryBorrowBooksData(BookDataCondition condition, GenericPageable genericPageable, UsersInfoVo usersInfo) {
		
		// 資料總筆數
		int dataTotalCount = borrowingRecordDataDao.queryBorrowBookCountByBookDataCondition(condition, usersInfo);
		
		genericPageable = Page.calculatePageable(dataTotalCount, genericPageable);
		
		logger.info("計算後的分頁資訊:" + genericPageable);
		
		// 計算查詢範圍
		int endRowNo = genericPageable.getShowDataCount() * genericPageable.getCurrentPage();
		int startRowNo = endRowNo - genericPageable.getShowDataCount() + 1;
		condition.setEndRowNo(endRowNo);
		condition.setStartRowNo(startRowNo);
		
		logger.info("計算後的條件資訊:" + condition);
		
		List<BorrowBookInfoMapping> borrowBookList = borrowingRecordDataDao.queryBorrowBookByBookDataCondition(condition, genericPageable, usersInfo);
		
		BorrowBookDataInfo BorrowbookDataInfo = BorrowBookDataInfo.builder().borrowBookList(borrowBookList).genericPageable(genericPageable).build();
		
		return BorrowbookDataInfo;
	}
	
	@Transactional(transactionManager = "oracleTransactionManager", rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public BorrowingRecord borrowBooks(UsersInfoVo usersInfo, BorrowBookVo borrowBookVo) {
		
		List<Inventory> dbInventory = inventoryInfoDao.findByInventoryIDAndStatus(borrowBookVo.getInventoryID(), "1");
		
		BorrowingRecord borrowingRecord = null;
		
		if (null != dbInventory && dbInventory.size() > 0) {
			
			// 獲取當前的日期和時間
			LocalDateTime currentDateTime = LocalDateTime.now();
	        
	        BorrowingRecordID borrowingRecordID = BorrowingRecordID.builder()
	        		.userID(usersInfo.getUserID())
	        		.inventoryID(dbInventory.get(0).getInventoryID()).build();
	       
	        // 新增借閱紀錄
	        borrowingRecord = BorrowingRecord.builder()
	        		.borrowingRecordID(borrowingRecordID)
	                .borrowingTime(currentDateTime)
	                .build();
	        
	        // 修改庫存狀態為出借中
	        dbInventory.get(0).setStatus("0");
			
	        return borrowingRecordInfoDao.save(borrowingRecord);
		}
		
		return null;
		
	}
	
	@Transactional(transactionManager = "oracleTransactionManager", rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public BorrowBookVo returnBooks(UsersInfoVo usersInfo, BorrowBookVo borrowBookVo) {
		
		BorrowingRecordID borrowingRecordID = BorrowingRecordID.builder()
        		.userID(usersInfo.getUserID())
        		.inventoryID(borrowBookVo.getInventoryID()).build();
		
		Optional<BorrowingRecord> optBorrowingRecord = borrowingRecordInfoDao.findById(borrowingRecordID);
		
		BorrowBookVo returnBookInfo = null;

		if (optBorrowingRecord.isPresent()) {
			
			// 獲取當前的日期和時間
			LocalDateTime currentDateTime = LocalDateTime.now();

			// 修改庫存狀態為在庫
			List<Inventory> dbInventory = inventoryInfoDao.findByInventoryIDAndStatus(borrowBookVo.getInventoryID(), "0");
	        dbInventory.get(0).setStatus("1");
			
	        // 更新書籍歸還時間
			BorrowingRecord dbBorrowingRecord = optBorrowingRecord.get();
			dbBorrowingRecord.setReturnTime(currentDateTime);
			
			returnBookInfo = BorrowBookVo.builder()
					.userID(usersInfo.getUserID())
					.inventoryID(borrowBookVo.getInventoryID())
					.returnTime(currentDateTime)
					.build();
			
		}
		
		return returnBookInfo;
		
	}
	
}
