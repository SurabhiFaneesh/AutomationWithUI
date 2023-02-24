package com.avaya.product.fetcher.ws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private static final Logger log = LogManager.getLogger(Product.class);

    private static final String JK_PROD_NAME = "name";

    private static final String JK_PROD_ID = "product_id";

    private static final String JK_PROD_CATEGORY = "category";

    public static final String PRODUCT_UNKNOWN = "UNKNOWN";

    String category;

    String name;

    String productID;

    public Product(String category, String name, String productID) {
        this.category = category;
        this.name = name;
        this.productID = productID;
    }

    public Product(JSONObject data) {
        try {
            this.category = data.getString("category");
            this.name = data.getString("name");
            this.productID = data.getString("product_id");
        } catch (JSONException e) {
            log.error("Failed to initialize product based on JSON:" + data.toString(), (Throwable)e);
        }
    }

    public JSONObject getJSONObject() {
        JSONObject productJson = new JSONObject();
        try {
            productJson.put("name", this.name);
            productJson.put("category", this.category);
            productJson.put("product_id", this.productID);
        } catch (JSONException e) {
            log.error("Failed to create JSON", (Throwable)e);
        }
        return productJson;
    }

    public String toString() {
        return "ProductCategory: " + this.category + "| ProductName: " + this.name + "| ProductID: " + this.productID;
    }

    public String getID() {
        return this.productID;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isValid() {
        return (this.name != null && !this.name.equals("UNKNOWN"));
    }
}

