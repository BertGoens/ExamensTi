import BO.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Tim on 4/06/2015.
 */
public class ProductPersistence {

    private EntityManager entityManager;

    public static void main(String[] args) {ProductPersistence pp = new ProductPersistence(); }

    public ProductPersistence()
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ProductPersistence");
        entityManager = entityManagerFactory.createEntityManager();
        Product p = new Product();
        p.setId(1);
        p.setProductName("test");
        addProduct(p);
        System.out.println(productExists("test"));
        System.out.println(listProductByCategory(1).toString());
        System.out.println(listCategories().toString());
    }

    public List<Product> listProductByCategory(int categoryId)
    {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.category.id = ?1", Product.class).setParameter(1, categoryId);
        return query.getResultList();
    }

    public List<Category> listCategories()
    {
        TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    public void addProduct(Product p)
    {
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();
    }

    public boolean productExists(String productName)
    {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.productName = ?1", Product.class).setParameter(1, productName);
        if(query.getResultList().isEmpty())
            return false;
        else
            return true;
    }
}
