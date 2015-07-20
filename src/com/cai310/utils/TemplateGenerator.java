package com.cai310.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.cai310.lottery.Constant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateGenerator {
	
	private Configuration cfg = null;
	
	private String encoding = "UTF-8";
	private String templatePath;
	
	public TemplateGenerator(String templatePath) throws IOException{
		cfg = new Configuration();
		cfg.setDefaultEncoding(encoding);
		cfg.setDirectoryForTemplateLoading(new File(templatePath));
		cfg.setNumberFormat("#");
		cfg.setObjectWrapper(new DefaultObjectWrapper());	
		this.templatePath = templatePath;
	}
	
	public TemplateGenerator(String templatePath,String encoding) throws IOException{
		this.encoding = encoding;
		cfg = new Configuration();
		cfg.setDefaultEncoding(encoding);
		cfg.setDirectoryForTemplateLoading(new File(templatePath));
		cfg.setNumberFormat("#");
		cfg.setObjectWrapper(new DefaultObjectWrapper());		
		this.templatePath = templatePath;
	}	
	public TemplateGenerator(Boolean index) throws IOException{
		cfg = new Configuration();
		cfg.setDefaultEncoding(encoding);
		cfg.setNumberFormat("#");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDirectoryForTemplateLoading(new File(Constant.ROOTPATH));
		cfg.addAutoImport("news", "/WEB-INF/macro/news.ftl");
	}
	 /**
     * 生成静态文件
     * @param ftlTemplate ftl模版文件
     * @param contents    ftl要用到的动态内容
     * @param saveFilename 保存文件名
     * @param realPath 文件绝对路径
     * @throws IOException
	 * @throws TemplateException 
     * @throws TemplateException
     */	
	public void create(String ftlTemplate, Map contents,String saveFilename,String realPath) throws IOException, TemplateException{
		Template temp = cfg.getTemplate(ftlTemplate);
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(realPath + "/" + saveFilename),encoding);
        temp.process(contents, out);
        out.flush();
        out.close();
    }     	

	
	/**
	 * 生成flt文件
	 * @param content
	 * @throws Exception
	 */
	public String makeFtl(String fileName, String content) throws Exception{		
//		String fileName =  System.currentTimeMillis() + ".flt";
		String path = templatePath + "/" + fileName;
		File f = new File(templatePath);
		if(!f.isDirectory()){
			f.mkdir();
		}		
		f = new File(path);
		if(f.exists()){	
			f.delete();		
		}		
		f.createNewFile();		
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(content.getBytes(encoding));			
		fos.flush();
		fos.close();
		return fileName;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
