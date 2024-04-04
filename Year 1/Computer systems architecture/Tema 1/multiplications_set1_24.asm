bits 32
global  start 

extern  exit
import  exit msvcrt.dll
        
segment  data use32 class=data 
    a db 10
    b db 15
    c db 7
    d dw 12
    
segment  code use32 class=code 
start: 
	mov eax,0
	mov edx,0
    mov al,[a]
    mov ah,10
    mul ah
    mov edx,eax
    mov al,5
    mov ah,[b]
    mul ah
    sub dx,ax
    mov eax,0
    mov al,[c]
    mov ah,5
    mul ah
    mov bx,[d]
    sub bx,ax
    add dx,bx
    
    
	
	push   dword 0 
	call   [exit] 
    