bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a - byte, b - word, c - double word, d - qword -Signed representation
    a db 8
    b dw 12
    c dd 50
    d dq 100
    

; our code starts here
segment code use32 class=code
    start:
        ;(a + b + c) - d + (b - c)
        mov al,[a]
        cbw
        add ax,[b]
        cwde
        add eax,[c] ; 
        cdq         ; EDX:EAX = (a + b + c)
        mov ecx,[d+0]
        mov ebx,[d+4]  ; EBX:ECX = d
        sub edx,ebx
        sbb eax,ecx  ; EDX:EAX = (a + b + c) - d
        mov ebx,edx
        mov ecx,eax  ; EBX:ECX = (a + b + c) - d
        mov ax,[b]
        cwde
        sub eax,[c]
        cdq         ; EDX:EAX = b - c
        add eax,ecx
        adc edx,ebx ; EDX:EAX= (a + b + c) - d + (b - c)
        
        
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
