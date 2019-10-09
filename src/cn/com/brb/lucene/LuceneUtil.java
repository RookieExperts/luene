package cn.com.brb.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.com.brb.lucene.entity.Article;

public class LuceneUtil {

	
	private Directory dir = null;
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws IOException{
		//生成索引库
		dir = FSDirectory.open(new File("G:\\temp\\index"));
        // 新建分析器对象
        Analyzer analyzer = new IKAnalyzer();
        // 新建配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        // 创建一个IndexWriter对象（参数一个索引库，一个配置）
        IndexWriter indexWriter = new IndexWriter(dir, config);
		return indexWriter;
	}
	
	public void addIndex(Article article) throws IOException {
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id",article.getId().toString(),Field.Store.YES));
        doc.add(new TextField("title",article.getTitle(),Store.YES));
        doc.add(new TextField("content",article.getContent(),Store.YES));
        writer.addDocument(doc);
        writer.close();
	}
	public static void main(String[] args) throws IOException {
		Article article = new Article();
		article.setId(222);
		article.setTitle("宣扬色情、赌博 42名主播被封禁5年");
		article.setContent("中国演出行业协会网络表演（直播）分会表示，近期，"
				+ "个别主播在直播过程中出现宣扬色情、宣扬赌博等违法违规内容，造成社会不良影响，损害行业形象。各直播平台严格自律，"
				+ "第一时间向分会上报了劣迹主播信息及其违规证据，分会组织内容评议委员会专家组进行审核，并经分会会长联席会讨论通过，正式确定了第四批网络表演（直播）行业主播“黑名单”。");
		LuceneUtil luceneUtil = new LuceneUtil();
		luceneUtil.addIndex(article);
	}
}
