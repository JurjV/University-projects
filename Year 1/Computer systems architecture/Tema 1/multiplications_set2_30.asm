bits 32
global  start 

extern  exit
import  exit  msvcrt.dll
        
segment  data use32 class=data 
    a db 17
    b db 15
    c db 7
    d db 12
    e dw 1156
    f dw 21
    g dw 5
    h dw 8
    
segment  code use32 class=code 
start: 
	;100/(e+h-3*a)
    mov eax,0
	mov edx,0
    mov ax,[e]
    add ax,[h]
    mov bx,ax
    mov eax,0
    mov al,3
    mov ah,[a]
    mul ah
    sub bx,ax
    mov eax,0
    mov ax,100
    div word bx
    
    
    
	
	push   dword 0 
	call   [exit] 
    