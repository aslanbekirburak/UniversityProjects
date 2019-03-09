#include <QtGui/QApplication>
#include "mainwindow.h"
#include <QApplication>
#include <QLabel>
#include <QPushButton>
#include <QtCore>

int main(int argc, char *argv[])//this is our main function
{
    QApplication a(argc, argv);

    MainWindow w;
    w.show();
    return a.exec();
}
