filename = 'exampleSignal.csv';
M = csvread(filename);
figure
findpeaks(M);
hold on;
plot(M);
hold off;
