bits 32
global  start 

extern  exit
import  exit  msvcrt.dll
        
segment  data use32 class=data 
    a db 2
    b db 15
    c db 7
    d db 12
    e dw 1156h
    f dw 21
    g dw 5
    h dw 8
    
segment  code use32 class=code 
start: 
	;(g+5)-a*d
    mov eax,0
	mov edx,0
    mov ax,[g]
    add ax,5
    mov dx,ax
    mov ax,0
    mov al,[a]
    mov ah,[d]
    mul ah ;ax=a*d
    sub dx,ax
    
    
	
	push   dword 0 
	call   [exit] 
    