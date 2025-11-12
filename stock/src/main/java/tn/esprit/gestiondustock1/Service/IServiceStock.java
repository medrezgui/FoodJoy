package tn.esprit.gestiondustock1.Service;



import tn.esprit.gestiondustock1.Entity.Stock;

import java.util.List;

public interface IServiceStock {

    Stock addOrUpdateStock(Stock newStock);


    Stock updateStock(Long id, Stock stock);
    void deleteStock(Long id);
    List<Stock> getAllStocks();
    Stock getStockById(Long id);

    void verifierAlertes();
}
