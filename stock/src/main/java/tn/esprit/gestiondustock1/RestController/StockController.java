package tn.esprit.gestiondustock1.RestController;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondustock1.Entity.Stock;
import tn.esprit.gestiondustock1.Repository.StockRepository;
import tn.esprit.gestiondustock1.Service.IServiceStock;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor

public class StockController {

    private final StockRepository stockRepository;

        private final IServiceStock stockService;

    // ✅ Endpoint POST pour ajouter un stock
    // ✅ Ajouter ou mettre à jour un stock automatiquement
    @PostMapping("/add")
    public Stock addOrUpdateStock(@RequestBody Stock stock) {
        return stockService.addOrUpdateStock(stock);
    }

        @PutMapping("/{id}")
        public Stock updateStock(@PathVariable Long id, @RequestBody Stock stock) {
            return stockService.updateStock(id, stock);
        }

        @DeleteMapping("/{id}")
        public void deleteStock(@PathVariable Long id) {
            stockService.deleteStock(id);
        }

        @GetMapping("/all")
        public List<Stock> getAllStocks() {
            return stockService.getAllStocks();
        }

        @GetMapping("/{id}")
        public Stock getStockById(@PathVariable Long id) {
            return stockService.getStockById(id);
        }



    @GetMapping("/stock-faible")
    public List<Stock> getStockFaible() {
        return stockRepository.findStocksFaibles();
    }

    @GetMapping("/presque-perime")
    public List<Stock> getPresquePerime() {
        LocalDateTime dateLimite = LocalDateTime.now().plusDays(7);
        return stockRepository.findStocksPresquePerimes(dateLimite);
    }


    @GetMapping("/test-alert")
    public String testAlertes() {
        stockService.verifierAlertes();
        return "Test d'alerte exécuté ✅";
    }

    }
