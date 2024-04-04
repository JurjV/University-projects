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
	;(2*d+e)/a
    mov eax,0
	mov edx,0
    mov al,[d]
    mov ah,2
    mul ah
    add ax,[e]
    mov bx,[a]
    mov bh,0
    div byte bx
    
    
    
	
	push   dword 0 
	call   [exit] 
    