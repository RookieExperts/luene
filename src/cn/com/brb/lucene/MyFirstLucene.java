package cn.com.brb.lucene;

import java.io.File;
import org.apache.lucene.document.Document;


import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.junit.Test;


public class MyFirstLucene {
    
    // 搜索索引
    @Test
    public void testSearch() throws Exception {
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        Directory directory = FSDirectory.open(new File("G:\\temp\\index"));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);
        // 第三步：创建一个indexSearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
        Query query = new TermQuery(new Term("content", "直播"));
        // 第五步：执行查询（显示条数）
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            // 文件编号
            String id = document.get("id");
            System.out.println(id);
            // 文件标题
            String title = document.get("title");
            System.out.println(title);
            // 文件内容
            String content = document.get("content");
            System.out.println(content);
            System.out.println("------------");
        }
        // 第七步：关闭IndexReader对象
        indexReader.close();

    }  
}