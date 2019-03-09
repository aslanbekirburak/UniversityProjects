img = imread('lena.png');
image(img);
imgGray = rgb2gray(img);
imshow(imgGray);

meanval = mean2(imgGray)
val = std2(imgGray)
[M,I] = min(imgGray(:))
[M1,I1] = max(imgGray(:))

[I_row, I_col] = ind2sub(size(imgGray),I)
M1 = min(min(imgGray))

[A_row, A_col] = ind2sub(size(imgGray),I1)
M2 = max(max(imgGray))
