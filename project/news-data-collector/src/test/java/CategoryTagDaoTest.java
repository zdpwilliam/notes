import com.william.news.dao.ICategoryTagDao;
import com.william.news.dao.INewsDao;
import com.william.news.model.NewsCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryTagDaoTest {

	@Autowired
	public ICategoryTagDao categoryTagDao;

	@Autowired
	private INewsDao newsDao;
	
	@Test
	public void testMyBatis() {
		NewsCategory category = new NewsCategory();
		category.setCategoryName("test123");
		category.setModifiable(1);
		category.setCoverPath("----");
		category.setSubstitutedTitle("test");
		categoryTagDao.addNewsCategory(category);
		System.out.println(category);
//		System.out.println("categoryTagDao :" + categoryTagDao.selectAllNewsCategories());
	}

	@Test
	public void testNewsDao() {
		System.out.println("newsDao :" + newsDao.selectNewsByCategoryId(0));
	}
	
}
