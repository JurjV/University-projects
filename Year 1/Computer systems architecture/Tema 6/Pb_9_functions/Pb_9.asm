;Read two numbers a and b (base 10) from the keyboard and calculate: (a+b)/(a-b). The quotient will be stored in a variable called "result" (defined in the data segment). The values are considered in signed representation.
bits 32

global start        


extern exit, printf, scanf         
import exit msvcrt.dll    
import printf msvcrt.dll    
import scanf msvcrt.dll     
                          
segment data use32 class=data
	a dd  0      
    b dd  0   
    c dd  0 
    d dd  0 
    result dd 0
	read_message_a  db "a=", 0  
	read_format_a  db "%d", 0       
	read_message_b  db "b=", 0  
	read_format_b  db "%d", 0
    result_format db "(%d+%d)/(%d-%d) = %d/%d = %d", 0
    
segment code use32 class=code
    start:
       
        push dword read_message_a 
        call [printf]      ; printing the message "a="
        add esp,4*1       
               
        push dword a       
        push dword read_format_a
        call [scanf]       ; reading the constant given to a from the keyboard
        add esp,8     
        
        
        push dword read_message_b 
        call [printf]      ; printing the message "b="
        add esp,4*1       
               
        push dword b       
        push dword read_format_b
        call [scanf]       ; reading the constant given to b from the keyboard
        add esp,8
        
        mov eax,[a]
        sub eax,[b]
        mov [c],eax; c= a-b
        push eax   ; push (a-b) on the stack
        mov eax,[a]
        add eax,[b]
        mov [d],eax; d= a+b
        cdq        ; convert (a+b) to qword 
        pop ebx
        idiv ebx     ; edx:eax= (a+b)/(a-b)
        mov [result],eax  ; result = eax
        
        push dword [result]
        push dword [c]
        push dword [d]
        push dword [b]
        push dword [a]
        push dword [b]
        push dword [a]
        push dword result_format  ;  printing the result in the console
        call [printf]
        add esp,4*8
        
        ; exit(0)
        push dword 0      ; place on stack parameter for exit
        call [exit]       ; call exit to terminate the program