package com.esunbank.e_library_spring_boot.tool;

import java.util.ArrayList;
import java.util.List;

import com.esunbank.e_library_spring_boot.vo.GenericPageable;

public class Page {

	public static GenericPageable calculatePageable(int dataTotalCount, GenericPageable genericPageable) {

		int currentPage = (genericPageable.getCurrentPage() > 0) ? genericPageable.getCurrentPage() : 1;
		
		// 一頁顯示多少筆資料
		int showDataCount = (genericPageable.getShowDataCount() > 0) ? genericPageable.getShowDataCount() : 5;
		
		// 一頁最多顯示多少分頁圖示
		int showPageSize = (genericPageable.getShowPageSize() > 0) ? genericPageable.getShowPageSize() : 3;
		
		// 至少要能有一筆資料數才能顯示一頁
		dataTotalCount = (dataTotalCount > 0) ? dataTotalCount : 1;
		
		// 計算總頁數
		int pageTotalCount = dataTotalCount % showDataCount == 0 ? (dataTotalCount / showDataCount) : (dataTotalCount / showDataCount) + 1;
		
		int startPage, endPage;
		
		// 一頁最多顯示n個分頁，如果資料數少於n頁，則show出所有分頁
		if (pageTotalCount >= showPageSize) {
			
			int half = showPageSize / 2; // 取得範圍中心點前後數量

			if (currentPage <= half) { // 當前頁面在前一半
				startPage = 1;
				endPage = showPageSize;
			} else if (currentPage >= pageTotalCount - half + 1) { // 當前頁面在後一半
				startPage = pageTotalCount - showPageSize + 1;
				endPage = pageTotalCount;
			} else { // 當前頁面在中間
				startPage = currentPage - half;
				endPage = currentPage + half;
			}
		} else {
			startPage = 1;
			endPage = pageTotalCount;
		}
		
		List<Integer> pageRange = new ArrayList();
		
		for (int i = startPage; i <= endPage; i++) {
			pageRange.add(i);
		}
		
		genericPageable = GenericPageable.builder()
				.currentPage(currentPage).dataTotalCount(dataTotalCount)
				.pageTotalCount(pageTotalCount).showDataCount(showDataCount)
				.showPageSize(showPageSize).startPage(startPage)
				.endPage(endPage).pageRange(pageRange)
				.build();
		
		return genericPageable;
	}
	
}
