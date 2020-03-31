package sg.edu.nus.studentmanagementsystem.services;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.nus.studentmanagementsystem.models.viewmodels.PagingTool;

@Service
public class PagingServiceImpl {
	public <T> HashMap<String, Object> Pagination(int currentPage, int pageSize, List<T> oList) {
		PagingTool pa = new PagingTool();
		pa.setCurrentPage(currentPage);
		pa.setPageSize(pageSize);
		pa.setTotalUnits(oList.size());
		Page<T> plist;

		if (currentPage + 1 == pa.getTotalPage() || currentPage == pa.getTotalPage()) {
			Pageable pageable = PageRequest.of(pa.getCurrentPage(), pa.getPageSize());
			plist = new PageImpl<T>(oList.subList(currentPage * pageSize, oList.size()), pageable, pa.getTotalUnits());
		} else {
			Pageable pageable = PageRequest.of(pa.getCurrentPage(), pa.getPageSize());
			plist = new PageImpl<T>(oList.subList(currentPage * pageSize, pageSize + currentPage * pageSize), pageable,
					pa.getTotalUnits());
		}
		ArrayList<Integer> pagelist = new ArrayList<Integer>();
		for (int i = 0; i < pa.getTotalPage(); i++) {
			pagelist.add(i);
		}
		HashMap<String, Object> pageOutput = new HashMap<String, Object>();
		pageOutput.put("pages", pagelist);
		pageOutput.put("list", plist);
		pageOutput.put("totalPage", pa.getTotalPage());
		return pageOutput;
	}
}