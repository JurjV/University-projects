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
    mov ax,[a]
    sub ax,[b]
    sub ax,[b]
    sub ax,[c]
    mov bx,[a]
    sub bx,[c]
    sub bx,[c]
    sub bx,[d]
    add ax,bx
    
	
	push   dword 0 
	call   [exit] 