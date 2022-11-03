package inventoryprogram;

/**
 *
 * @author myers
 */

public abstract class Part {
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Part(int id, String name, double price, int stock, int min, int max){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
    }
    
    public void setId(int idInput) {
        this.id = idInput;
    }
    
    public void setName(String nameInput) {
        this.name = nameInput;
    }

    public void setPrice(double priceInput) {
        this.price = priceInput;
    }
    
    public void setStock(int stockInput) {
        this.stock = stockInput;
    }

    public void setMin(int minInput) {
        this.min = minInput;
    }

    public void setMax(int maxInput) {
        this.max = maxInput;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    
}