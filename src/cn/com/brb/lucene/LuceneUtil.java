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
	 * ��ȡIndexWriterʵ��
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws IOException{
		//����������
		dir = FSDirectory.open(new File("G:\\temp\\index"));
        // �½�����������
        Analyzer analyzer = new IKAnalyzer();
        // �½����ö���
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        // ����һ��IndexWriter���󣨲���һ�������⣬һ�����ã�
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
		article.setTitle("����ɫ�顢�Ĳ� 42�����������5��");
		article.setContent("�й��ݳ���ҵЭ��������ݣ�ֱ�����ֻ��ʾ�����ڣ�"
				+ "����������ֱ�������г�������ɫ�顢����Ĳ���Υ��Υ�����ݣ������᲻��Ӱ�죬����ҵ���󡣸�ֱ��ƽ̨�ϸ����ɣ�"
				+ "��һʱ����ֻ��ϱ����Ӽ�������Ϣ����Υ��֤�ݣ��ֻ���֯��������ίԱ��ר���������ˣ������ֻ�᳤��ϯ������ͨ������ʽȷ���˵�����������ݣ�ֱ������ҵ����������������");
		LuceneUtil luceneUtil = new LuceneUtil();
		luceneUtil.addIndex(article);
	}
}
