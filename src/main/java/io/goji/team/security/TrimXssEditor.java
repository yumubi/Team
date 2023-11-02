//package io.goji.team.security;
//
//import cn.hutool.http.HtmlUtil;
//import cn.hutool.poi.excel.cell.CellEditor;
//import org.apache.poi.ss.usermodel.Cell;
// TODO: 2/11/2023 该配置失效 
///**
// * 读取excel的时候，去除掉html相关的标签  避免xss注入
// */
//public class TrimXssEditor implements CellEditor {
//
//    @Override
//    public Object edit(Cell cell, Object value) {
//        if (value instanceof String) {
//            return HtmlUtil.cleanHtmlTag(value.toString());
//        }
//        return value;
//    }
//}
