package com.esunbank.e_library_spring_boot.conrtoller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esunbank.e_library_spring_boot.vo.GenericPageable;
import com.esunbank.e_library_spring_boot.vo.UsersInfoVo;
import com.esunbank.e_library_spring_boot.vo.BorrowBookVo;
import com.esunbank.e_library_spring_boot.vo.BookDataCondition;
import com.esunbank.e_library_spring_boot.vo.BookDataInfo;
import com.esunbank.e_library_spring_boot.vo.BorrowBookDataInfo;
import com.esunbank.e_library_spring_boot.entity.BorrowingRecord;
import com.esunbank.e_library_spring_boot.service.FrontEndService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/FrontEndController")
public class FrontEndController {

	private static Logger logger = LoggerFactory.getLogger(FrontEndController.class);

	@Autowired
	private HttpSession httpSession;
	
	@Resource(name = "sessionUsersInfo")
	private UsersInfoVo usersInfo;
	
	@Autowired
	private FrontEndService frontEndService;
	
	@ApiOperation(value = "圖書館-前臺-查詢書籍列表")
	@GetMapping(value = "/queryBookData")
	public ResponseEntity<BookDataInfo> queryBooksData(@RequestParam(required = false) String bookName,
			 @RequestParam int currentPage, @RequestParam int showDataCount, @RequestParam int showPageSize) {
	
		BookDataCondition condition = BookDataCondition.builder()
				.bookNameKeyword(bookName).status("1") //指查詢有在庫的書籍
				.build();

		GenericPageable genericPageable = GenericPageable.builder().currentPage(currentPage)
				.showDataCount(showDataCount).showPageSize(showPageSize).build();

		BookDataInfo bookDataInfo = frontEndService.queryBooksData(condition, genericPageable);		
		
		return ResponseEntity.ok(bookDataInfo);
	}
	
	@ApiOperation(value = "圖書館-前臺-查詢借閱書籍列表")
	@GetMapping(value = "/queryborrowBooksData")
	public ResponseEntity<BorrowBookDataInfo> queryBorrowBooksData(@RequestParam(required = false) String bookName,
			 @RequestParam int currentPage, @RequestParam int showDataCount, @RequestParam int showPageSize) {
	
		BookDataCondition condition = BookDataCondition.builder()
				.bookNameKeyword(bookName).status("0") //指查詢借出的書籍
				.build();

		GenericPageable genericPageable = GenericPageable.builder().currentPage(currentPage)
				.showDataCount(showDataCount).showPageSize(showPageSize).build();

		BorrowBookDataInfo borrowBookDataInfo = frontEndService.queryBorrowBooksData(condition, genericPageable, usersInfo);		
		
		return ResponseEntity.ok(borrowBookDataInfo);
	}
	
	@ApiOperation(value = "圖書館-前臺-借閱書籍")
	@PostMapping(value = "/borrowBooks")
	public ResponseEntity<BorrowingRecord> borrowBooks(@RequestBody BorrowBookVo borrowBookVo) {
		
		/*
		{
		  "inventoryID": 1
		}

		{
		  "inventoryID": 5
		}
		*/
		
		BorrowingRecord borrowingRecord = frontEndService.borrowBooks(usersInfo, borrowBookVo);
		
		return  ResponseEntity.ok(borrowingRecord);
	}

	@ApiOperation(value = "圖書館-前臺-歸還書籍")
	@PutMapping(value = "/returnBooks")
	public ResponseEntity<BorrowBookVo> returnBooks(@RequestBody BorrowBookVo borrowBookVo) {
		
		BorrowBookVo returnBookInfo = frontEndService.returnBooks(usersInfo, borrowBookVo);
		
		return ResponseEntity.ok(returnBookInfo);
	}
}
