package com.mlh.buss.content;

import com.jfinal.core.Controller;
import com.mlh.model.Content;

public class ContentController extends Controller {
	public void list() {
		int pageNumber = getParaToInt(0, 1);
		setAttr("page", Content.dao.paginate(pageNumber, 20, " where 1 = 1 "));
		render("list.html");
	}

	public void edit() {

		String id = getPara(0);

		Content bean = Content.dao.findById(id);

		setAttr("bean", bean);

		render("edit.html");
	}
}
