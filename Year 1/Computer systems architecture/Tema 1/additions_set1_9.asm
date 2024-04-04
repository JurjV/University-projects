;a=10,b=20,c=30,d=40
bits 32
global  start 

extern  exit
import  exit msvcrt.dll
        
segment  data use32 class=data 
    a db 10
    b db 20
    c db 30
    d db 40
    
segment  code use32 class=code 
start: 
	mov eax,0
	mov ebx,0
    mov ax,[d]
    add ax,[d]
    sub ax,[b]
    mov bx,[c]
    sub bx,[a]
    add ax,bx
    add ax,[d]
	
	push   dword 0 
	call   [exit] 