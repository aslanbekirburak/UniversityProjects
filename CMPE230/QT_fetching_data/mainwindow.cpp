#include <QRegExp>
#include "ui_mainwindow.h"
#include"mainwindow.h"
#include <QtGui>
bool usd=false;
bool eur=false;
bool gbp=false;
bool cny=false;
MainWindow::MainWindow(QWidget *parent) :  QMainWindow(parent),ui(new Ui::MainWindow)//we set the background of the program in here
{
    ui->setupUi(this);
    label=new QLabel("Connecting");
    QHBoxLayout *layout = new QHBoxLayout;

    layout->addWidget(label);
    setLayout(layout);

}
MainWindow::~MainWindow()//this is destructure function
{
    delete ui;
}
void MainWindow::replyFinished(QNetworkReply *reply){//this function connect the web address and fetch the data which is needed to convert

    QString str;//this is for printing in a label to show which conversion we used
    QString str1;//this is for printing in a label to show what the result of the conversion is
    int pos=0;

    QString data=(QString)reply->readAll();

    QRegExp rx("(\\d+\\.\\d+)");//this is the key which we use in seearch of the value user want to see

    if ( rx.indexIn(data, pos) != -1 )//this control block help us deciding which value we need to show
    {
        if(usd==true)
        {
        str = QString("TL/USD:  ") + rx.cap(1);    // rate found
        }
        else if(eur==true)
        {
        str = QString("TL/EUR:  ") + rx.cap(1);    // rate found
        }
        else if(gbp==true)
        {
            str = QString("TL/GBP:  ") + rx.cap(1);    // rate found
        }
        else if(cny==true)
        {
            str = QString("TL/CNY:  ") + rx.cap(1);    // rate found
        }
    }
    else {
      str = QString("Error") ;
    }
    double rate=rx.cap(1).toDouble();//we convert the value which fecthed from the web from sting to double because
     // we need a double value to calculate the conversion which is expected
    double a=on_TL_edit_returnPressed()*rate;//this is an variable which is uesed for keeping the data which is result of the calculation of rate and input money
    str1=QString::number(a);//we convert the result of the conversion to string for printing the screen
    ui->rate_label->setText(str);//we print the the result which is string
     //in this control block we check which conversion is wanted from us and print the screen its converted status
    if(usd==true)
    {
        ui->amount_label->setText(str1+" USD");
    }
    else if(eur==true)
    {
        ui->amount_label->setText(str1+" EUR");
    }
    else if(gbp==true)
    {
        ui->amount_label->setText(str1+" GBP");
    }
    else if(cny==true)
    {
        ui->amount_label->setText(str1+" CNY");
    }
    eur=false;gbp=false;cny=false;usd=false;//this is for the second useage because
    //if some of these are true we face problems in checking blocks in the second usage
}

//when user click the USD button this function runs, connect the address
//and fetch the rate which we need to convert TL value to USD
void MainWindow::on_USD_clicked()
{
    usd=true;
    manager = new QNetworkAccessManager(this) ;
    connect(manager, SIGNAL(finished(QNetworkReply *)),this, SLOT(replyFinished(QNetworkReply *)));
    manager->get(QNetworkRequest(QUrl("https://api.fixer.io/latest?base=TRY&symbols=USD")));
}

//when user click the EUR button this function runs, connect the address
//and fetch the rate which we need to convert TL value to EUR
void MainWindow::on_EUR_clicked()
{
    eur=true;
    manager = new QNetworkAccessManager(this) ;
    connect(manager, SIGNAL(finished(QNetworkReply *)),this, SLOT(replyFinished(QNetworkReply *)));
    manager->get(QNetworkRequest(QUrl("https://api.fixer.io/latest?base=TRY&symbols=EUR")));
}

//when user click the GBP button this function runs, connect the address
//and fetch the rate which we need to convert TL value to GBP
void MainWindow::on_GBP_clicked()
{
    gbp=true;
    manager = new QNetworkAccessManager(this) ;
    connect(manager, SIGNAL(finished(QNetworkReply *)),this, SLOT(replyFinished(QNetworkReply *)));
    manager->get(QNetworkRequest(QUrl("https://api.fixer.io/latest?base=TRY&symbols=GBP")));
}

void MainWindow::on_CNY_clicked()
{
    cny=true;
    manager = new QNetworkAccessManager(this) ;
    connect(manager, SIGNAL(finished(QNetworkReply *)),this, SLOT(replyFinished(QNetworkReply *)));
    manager->get(QNetworkRequest(QUrl("https://api.fixer.io/latest?base=TRY&symbols=CNY")));
}

//when user click the CNY button this function runs, connect the address
//and fetch the rate which we need to convert TL value to CNY

//this function convert TL's value to double in order to calculate the result of the conversion
int MainWindow::on_TL_edit_returnPressed()
{
    QString XMAX=ui->TL_edit->text();//take the string from the eser
    double xMax=XMAX.toDouble();//convert string to double
    return xMax;//return the double value
}
