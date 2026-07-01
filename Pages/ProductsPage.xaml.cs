using EKZ_myself.AppData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace EKZ_myself.Pages
{
    /// <summary>
    /// Логика взаимодействия для ProductsPage.xaml
    /// </summary>
    public partial class ProductsPage : Page
    {
        MainWindow mainWindow;
        public ProductsPage()
        {
            InitializeComponent();

            mainWindow = Application.Current.MainWindow as MainWindow;
            Users currentUser = AppConnect.currentUser;

            if (currentUser != null && (currentUser.idrole == 1 || currentUser.idrole == 2))
            {
                mainWindow.userName.Text == currentUser.name;
                managment.Visibility = Visibility.Visible;
            }
            else if (currentUser == null || currentUser.idrole == 3)
            {
                mainWindow.userName.Text = currentUser != null ? currentUser.name : "Гость";
            }

            listProducts.ItemsSource= AppConnect.testEntities.Product.ToList();

            Fill();
        }

        public void Fill()
        {

        }

    }
}
