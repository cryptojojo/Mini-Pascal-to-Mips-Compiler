program arrayAssign;

var iter : integer;
var arr: array[0:4] of integer;

begin

  arr[0] := 20;
  write(arr[0]);

  arr[1] := 45;
  write(arr[1]);

  arr[2] := arr[0]+arr[1];
  write(arr[2]);

  write(arr[2]/2)


end
.
