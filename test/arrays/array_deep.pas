program arrayWhileLoop;

var inOne, inTwo: integer;

var arr: array[0:3] of integer;

begin

read(inOne);
read(inTwo);

arr[0] := inOne;
arr[1] := inTwo;

arr[2] := arr[0] * arr[1];

while arr[2] > 0 do
begin

 if arr[2] > 5 then
   begin
    write(arr[2]);
   end
   else
   begin
    write(999);
   end;

 arr[2] := arr[2]-1;
end;

end
.
