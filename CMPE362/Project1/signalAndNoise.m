%question 1 answers

x=-100:100;
y1=sin(x);
y2=sin(50*x);
y3=50*sin(x);
y4=sin(x+50);
y5=sin(x+50);
y6=50*sin(50*x);
y7=x.*sin(x);
y8=sin(x)./x;
figure %made a figure and subplots on it
subplot(4,2,1)
plot(x,y1)
subplot(4,2,2)
plot(x,y2)
subplot(4,2,3)
plot(x,y3)
subplot(4,2,4)
plot(x,y4)
subplot(4,2,5)
plot(x,y5)
subplot(4,2,6)
plot(x,y6)
subplot(4,2,7)
plot(x,y7)
subplot(4,2,8)
plot(x,y8)

%question 2 answers

x=-20:20;
y1=sin(x);
y2=sin(50*x);
y3=50*sin(x);
y4=sin(x+50);
y5=sin(x+50);
y6=50*sin(50*x);
y7=x.*sin(x);
y8=sin(x)./x;
y9= y1+y2+y3+y4+y5+y6+y7+y8;
figure %made a figure and subplots on it
subplot(5,2,1)
plot(x,y1)
subplot(5,2,2)
plot(x,y2)
subplot(5,2,3)
plot(x,y3)
subplot(5,2,4)
plot(x,y4)
subplot(5,2,5)
plot(x,y5)
subplot(5,2,6)
plot(x,y6)
subplot(5,2,7)
plot(x,y7)
subplot(5,2,8)
plot(x,y8)
subplot(5,2,9)
plot(x,y9)

%question 3 answers

z=randn(1,41);
y10=z;
y11=z+x;
y12=z+sin(x);
y13=z .* sin(x);
y14=x .* sin(z);
y15=sin(x+z);
y16=z .* sin(50 * x);
y17=sin(x+50 * z);
y18=sin(x)./z;
y19=y11+y12+y13+y14+y15+y16+y17+y18;
figure %made a figure and subplots on it
subplot(5,2,1);
plot(x,y10)
subplot(5,2,2);
plot(x,y11)
subplot(5,2,3);
plot(x,y12)
subplot(5,2,4);
plot(x,y13)
subplot(5,2,5);
plot(x,y14)
subplot(5,2,6);
plot(x,y15)
subplot(5,2,7);
plot(x,y16)
subplot(5,2,8);
plot(x,y17)
subplot(5,2,9);
plot(x,y18)
subplot(5,2,10);
plot(x,y19)

%question 4 answers

z=rand(1,41);
y20=z;
y21=z+x;
y22=z+sin(x);
y23=z .* sin(x);
y24=x .* sin(z);
y25=sin(x+z);
y26=z .* sin(50 * x);
y27=sin(x+50 * z);
y28=sin(x)./z;
y29=y21+y22+y23+y24+y25+y26+y27+y28;
figure %made a figure and subplots on it
subplot(5,2,1);plot(x,y20)
subplot(5,2,2);plot(x,y21)
subplot(5,2,3);plot(x,y22)
subplot(5,2,4);plot(x,y23)
subplot(5,2,5);plot(x,y24)
subplot(5,2,6);plot(x,y25)
subplot(5,2,7);plot(x,y26)
subplot(5,2,8);plot(x,y27)
subplot(5,2,9);plot(x,y28)
subplot(5,2,10);plot(x,y29)

%question 5 answers
z=randn([1,10000]);
r1=1 .* z+0;
r2=2 .* z+0;
r3=4 .* z+0;
r4=16 .* z+0;
figure
subplot(2,2,1);hist(r1)
subplot(2,2,2);hist(r2)
subplot(2,2,3);hist(r3)
subplot(2,2,4);hist(r4)

%question 6 answers
r5=1 .* z+10;
r6=2 .* z+20;
r7=1 .* z+(-10);
r8=2 .* z+(-20);
figure
subplot(2,2,1);hist(r5)
subplot(2,2,2);hist(r6)
subplot(2,2,3);hist(r7)
subplot(2,2,4);hist(r8)

%question 7 answers

z=rand([1 10000])-0.5;
r11=sqrt(12.*1) .* z+0;
r21=sqrt(12.*4) .* z+0;
r31=sqrt(12.*16) .* z+0;
r41=sqrt(12.*256) .* z+0;
figure
subplot(2,2,1);hist(r11)
subplot(2,2,2);hist(r21)
subplot(2,2,3);hist(r31)
subplot(2,2,4);hist(r41)

%question 8 answers
z=rand(100000 ,1)-0.5
r61=sqrt(12.*1) .* z+10;
r71=sqrt(12.*4) .* z+20;
r81=sqrt(12.*1) .* z-10;
r91=sqrt(12.*4) .* z-20;
figure
subplot(2,2,1); hist(r61)
subplot(2,2,2); hist(r71)
subplot(2,2,3); hist(r81)
subplot(2,2,4); hist(r91)