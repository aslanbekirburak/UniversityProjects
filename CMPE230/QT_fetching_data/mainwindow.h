#include <QtGui>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QMainWindow>
namespace Ui {
class MainWindow;
}
class MainWindow : public QMainWindow
{
        Q_OBJECT

    public:
        MainWindow(QWidget *parent = 0);
        ~MainWindow();
    public slots:
        void replyFinished(QNetworkReply * reply);

    private slots:

        void on_USD_clicked();

        void on_EUR_clicked();

        void on_GBP_clicked();

        void on_CNY_clicked();

        int on_TL_edit_returnPressed();

private:
        QLabel *label;
        QNetworkAccessManager *manager;
        Ui::MainWindow *ui;
};

