Sets
j   'customers' /
$include customers.txt
/

i   'transshipment centers' /
$include trcenters.txt
/

t   'vehicle type'    /small, large/

r   'truck' / 1*5 /;

Alias (j,jj);

scalar M /1000000/;

parameters

dv(j)   'demand volume of each customer' /
$include demand-volume.txt
/
dw(j)   'demand weight of each customer' /
$include demand-weight.txt
/
uc(j)   'unit cost of each customer' /
$include trans_cost.txt
/

cl(j,jj)    'clusterability'       /
$include clusterability.txt
/

ctc(j,i)    'customer transshipment center'   /
$include customer-TC.txt
/

f(t)    'freight cost' /
    small 125
    large 250
/

maxCapacity(t)  'maximum capacity of type t' /
    small 18
    large 33
/;

Table dsc(j,t)    'direct shipment cost'
$include direct-shipment-cost.txt
;

Positive Variables
    mc(t,r)  'maximum cost that r truck of type t serves'
    dc(t,r) 'extra direct shipment cost that r truck of type t serves';

Binary Variable dtc(t, r, j)    'if t type of r truck served j customer';
Binary Variable itc(j)  'if j customer is served indirectly';

Free Variable
z     'objective function';

Equations
    cost    'objective function'
    maxCustomerPerTruck(t,r)  'max customer per truck constraint'
    clusterability(t,r,j,jj)   'clusterability constraint'
    truckMaxVolume(t,r)    'small truck max volume constraint'
    maxCost(t,r,j)    'maximum cost that r truck of type t serves'
    extraDirectCost(t,r)    'extra direct cost for small truck constraint'
    customerIsServed(j)  'customer should be served directly or indirectly, this makes sure that customer is served';

cost..    z =e= sum(t, sum(r, mc(t,r) + dc(t,r))) + sum(j, dw(j) * uc(j) * itc(j));
maxCustomerPerTruck(t,r)..    sum(j, dtc(t,r,j) ) =l= 3;
clusterability(t,r,j,jj)..    dtc(t,r,j) + dtc(t,r,jj) =l= 1 + M * cl(j,jj);
truckMaxVolume(t,r)..    sum(j, dtc(t,r,j) * dv(j)) =l= maxCapacity(t);
maxCost(t,r,j)..              dtc(t,r,j) * dsc(j,t) =l= mc(t,r);
extraDirectCost(t,r)..        (sum(j, dtc(t,r,j)) - 1) * f(t) =l= dc(t,r);
customerIsServed(j)..      sum(t, sum(r, dtc(t,r,j))) + itc(j) =e= 1;

Model project /all/ ;

Solve project using MIP minimizing z ;
