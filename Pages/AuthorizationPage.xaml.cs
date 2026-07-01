using EKZ_myself.AppData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
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
    /// Логика взаимодействия для AuthorizationPage.xaml
    /// </summary>
    public partial class AuthorizationPage : Page
    {
        public AuthorizationPage()
        {
            InitializeComponent();
        }

        private void ButtonClick_Authorization(object sender, RoutedEventArgs e)
        {
            string login = loginField.Text;
            string password = passwordField.Password;

            Users users = AppConnect.testEntities.Users.FirstOrDefault(u => u.name == login && password == password);
            if (users != null)
            {
                AppConnect.currentUser = users;
                MessageBox.Show($"Добро пожаловавть {users.name}");
                AppFrame.mainFrame.Navigate(new ProductsPage());
            }
            else
            {
                MessageBox.Show($"Неверно введён логин или пароль!");
            }

        }
        catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        private void ButtonClick_GuestEntry(object sender, RoutedEventArgs e)
        {
            AppConnect.currentUser = null;
            AppFrame.mainFrame.Navigate(new ProductsPage());
        }
    }
}
