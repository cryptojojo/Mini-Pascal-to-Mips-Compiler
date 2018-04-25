program arrayWhileLoop;

var iter : integer;
var arr: array[0:5] of integer;

begin

  iter:= 1;

  while iter < 20 do
  begin
    arr[iter] := iter*5;
    iter := iter+1
  end;

  iter:= 1;

  while iter < 5 do
  begin
    write(arr[iter]);
    iter := iter+1
  end;

end
.
