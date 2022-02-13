package com.agg.model;

import lombok.Data;

@Data
public class FileSplit {
    /**保存句子拆分后的字符串数组*/
    private String[] sentenceSplits;
    /**保存图片拆分后的字符串数组*/
    private String[] imageSplits;
    /**保存表格拆分后的字符串数组*/
    private String[] formSplits;

    public String toString(){
        String toString= "sentenceSplit: "+this.getSentenceSplits()+"\n"
                +"imageSplits: " + this.getImageSplits()+"\n"
                + "formSplits: " + this.getFormSplits();
        return toString;
    }
}
