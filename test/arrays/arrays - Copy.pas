program BitcoinConversion;

var dollars, yen, bitcoins : integer;

var arr: array[0:5] of integer;

var dolla : integer;

var arr2: array[0:2] of integer;


begin

  arr[0] := 1;

  arr[1] := 2;
  arr[2] := 3;
  arr[3] := 4;
    
  write(arr[0]);
  write(arr[1]);
  write(arr[2]);
  write(arr[3]);

  dollars := 10000;
  yen := dollars*107;
  bitcoins := dollars / 8000;

  write(dollars);
  write(yen);
  write(bitcoins);

end
.
