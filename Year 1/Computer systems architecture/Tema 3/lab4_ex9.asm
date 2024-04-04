bits 32 

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;Given the word A and the byte B, compute the doubleword C as follows:
    ;the bits 0-3 of C are the same as the bits 6-9 of A
    ;the bits 4-5 of C have the value 1
    ;the bits 6-7 of C are the same as the bits 1-2 of B
    ;the bits 8-23 of C are the same as the bits of A
    ;the bits 24-31 of C are the same as the bits of B
    
    
    a dw 1001001100110101b
    b db 01100110b 
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        mov ebx,0 ; the result will be on ebx
        
        mov ax,[a]
        and ax,0000001111000000b ; we isolate the bits 6-9 of A
        mov cl,6
        ror ax,cl ; we rotate 6 positions to the right
        or bx,ax  ; we store the bits in the result
        
        or bx,0000000000110000b ; we put the value 1 on bits 4-5
        
        mov ax,0
        mov al,[b]
        and ax,0000000000000110b ; we isolate the bits 1-2 of B
        mov cl,5
        rol ax,cl ; we rotate 5 positions to the left
        or bx,ax ; we store the bits in the result
        
        mov eax,0
        mov ax,[a]
        mov cl,8
        rol eax,cl ; we rotate 8 positions to the left
        or ebx,eax ; we store the bits in the result
        
        mov eax,0
        mov al,[b]
        mov cl,24
        rol eax,cl ; we rotate 24 positions to the left
        or ebx,eax ; we store the bits in the result
        
        mov[c],ebx 
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
