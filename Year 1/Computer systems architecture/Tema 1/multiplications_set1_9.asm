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
    mov al,[b]
    sub al,[a]
    add al,2
    mov ah,20
    mul ah
    mov edx,eax
    mov ah,[c]
    mov al,10
    mul ah
    sub dx,ax
    mov edx,eax
    mov ah,3
    mul ah
    mov edx,eax
    mov al,[d]
    sub al,3
    mov ah,2
    mul ah
    add ax,dx
    
    
	
	push   dword 0 
	call   [exit] 
    