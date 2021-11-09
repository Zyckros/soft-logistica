package comampleftlogisticaaddata

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.softlogistica.R
import com.example.softlogistica.model.budget.Budget
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product

class LoadData {
    companion object{

        /**
         * Lista de productos
         */
        var productVehicle : Product =  Product(id =1, internal_code = 134134, description = "Taladro deWalt de 3000W ",
            broad_description = "Taladro amarillo",buy_date ="20/02/2021"  , registration_date = "12/02/2021", name = "Taladro", brand = "DeWalt", model = "rocket", license = "012341450Z",serial_number =  "23452345",buy_price =  78000.00, loadmax_m3 = 5000.00, loadmax_tons = 15000.00,
            itv = "02/05/2023",
            insurante = "4H2H222P3H", high = 4198, width = 5648, depth = 4500 , weight = 2300, fuel = "GASOLINA", number_chassis =  "5H652T534334TY", frequenzy_hz = null, intensity_amp =  null,  length_mm = null,license_trailer =  "5G5U7I35354TY3T54",
            negociated_price = null,  tare = 600, number_axle = "2X4" , power =  null,  par = 345, voltage = 220, rentalprice_day = 3.00,  rentalprice_hour = 3500.00, rentalprice_km = null, rentalprice_month =  100000.00,
            journey_price =  20.00, sale_price = null, photo_product = "https://www.mimbral.cl/media/catalog/product/cache/1/image/1200x1200/9df78eab33525d08d6e5fb8d27136e95/0/2/026004043_17.jpg", technicalsheet = "12/12/2020",
            key_words = "taladro amarillo deWalt", type = "Marcos Santana", manufactureDate = "20/02/2021", supplier = "Camiones Sosa S.A.", familyID = 1, available = true)

        var productTool : Product =  Product(id =2, internal_code = 134134, description = "Dumper para desplazamiento de tierra",
            broad_description = "Dumper",buy_date =  "20/06/2021", registration_date = "12/02/2021",name =  "Dumper", brand = "Benlord", model = "3500A", license = "012341450Z",serial_number =  "23452345",buy_price =  78000.00, loadmax_m3 = 5000.00, loadmax_tons = 15000.00,
            itv = "02/05/2023",
            insurante = "4H2H222P3H", high = 4198, width = 5648, depth = 4500 , weight = 2300, fuel = "GASOLINA", number_chassis =  "5H652T534334TY", frequenzy_hz = null, intensity_amp =  null,  length_mm = null,license_trailer =  "5G5U7I35354TY3T54",
            negociated_price = null,  tare = 600, number_axle = "2X4" , power =  null,  par = 345, voltage = 220, rentalprice_day = 3.00,  rentalprice_hour = 3500.00, rentalprice_km = null, rentalprice_month =  100000.00,
            journey_price =  20.00, sale_price = null, photo_product = "http://ferrospedros.com/wp-content/uploads/2016/07/IMG_9194.jpg", technicalsheet = "12/12/2020",
            key_words = "Dumper Benlord naranja", type = "Marcos Santana", manufactureDate = "20/02/2021", supplier = "Camiones Sosa S.A.", familyID = 2, available = true)

        var auxiliaryProduct : Product =  Product(id =3, internal_code = 134134, description = "Cabeza tractora para el remolque de hasta 50 kg",
            broad_description = "Cabeza tractora Scania",buy_date =  "12/02/2021", registration_date = "12/02/2021",name =  "Scania", brand = "Scania", model = "750A", license = "012341450Z",serial_number =  "23452345",buy_price =  78000.00, loadmax_m3 = 5000.00, loadmax_tons = 15000.00,
            itv = "02/05/2023",
            insurante = "4H2H222P3H", high = 4198, width = 5648, depth = 4500 , weight = 2300, fuel = "GASOLINA", number_chassis =  "5H652T534334TY", frequenzy_hz = null, intensity_amp =  null,  length_mm = null,license_trailer =  "5G5U7I35354TY3T54",
            negociated_price = null,  tare = 600, number_axle = "2X4" , power =  null,  par = 345, voltage = 220, rentalprice_day = 3.00,  rentalprice_hour = 3500.00, rentalprice_km = null, rentalprice_month =  100000.00,
            journey_price =  20.00, sale_price = null, photo_product = "https://upload.wikimedia.org/wikipedia/commons/5/5c/ISUZU_Elf_6th_Generation%2C_Post_office_truck.jpg", technicalsheet = "12/12/2020",
            key_words = "camion marcos santana rojo 3500", type = "Marcos Santana", manufactureDate = "20/02/2021", supplier = "Camiones Sosa S.A.", familyID = 3,available = true)

        var products : MutableList<Product?>? =  mutableListOf(productTool, productVehicle, auxiliaryProduct)


        /**
         * Elementos de Menu
         */
        var auxiliaryMeansStorage = ProductMenu(id=1,image= R.drawable.ic_baseline_build_24, name="Herramientas")
        var machineryStorage = ProductMenu(id=2, image= R.drawable.ic_baseline_rv_hookup_24, name="Maquinaria")
        var transportStorage = ProductMenu(id=3, image=R.drawable.ic_baseline_local_shipping_24, name="Transporte")

        val menu : List<ProductMenu> = mutableListOf(auxiliaryMeansStorage, machineryStorage, transportStorage)


        val client = User(id=1,name= "Cliente",token="1j1jk1h341jh41j23h4124jh3123jh4", role="client",email= "cliente@email.com", "client")
        val employee = User(id=2, name="Empleado",token =  "24h52jkl45h2l345h23jl45h", role = "employee",email = "empleado@email.com", "employee")
        val admin = User(id=3, name="Administrador",token = "5452ñ3452ñ34l5h2ñ34l5h", role = "admin",email = "administrador@email.com", "admin")

        val users : List<User> = listOf(client, employee, admin)

        val product = Cart(1L,1L,"20/09/2021", "24/09/2021", "rental")
        val product2 = Cart(2L,2L,null, null, "buy")
        val product3 = Cart(3L,3L,"28/09/2021", "13/10/2021", "rental")


        val cartListProducts : MutableList<Cart?> = mutableListOf(product, product2, product3)


        @RequiresApi(Build.VERSION_CODES.O)
        val budget1 = Budget(id = null, "AER2345ASD", "20/11/2021", 260.90, 1L,2L, 1, "pending")

        @RequiresApi(Build.VERSION_CODES.O)
        val budget2 = Budget(id = null, "2345WREG43", "20/11/2021",570.90, 1L,2L, 1, "pending")

        @RequiresApi(Build.VERSION_CODES.O)
        val budget3 = Budget(id = null, "092H544", "20/11/2021",780.40, 1L,2L, 1, "accepted")

        @RequiresApi(Build.VERSION_CODES.O)
        val budget4 = Budget(id = null, "234P5TN34", "20/11/2021", 450.50, 1L,2L, 1, "accepted")

        val budgetList : MutableList<Budget?>? = mutableListOf(budget1, budget2, budget3, budget4)

    }
}

data class User(
    val id: Long?,
    val name: String?,
    val token: String?,
    val role: String?,
    val email: String,
    val password: String,
)
