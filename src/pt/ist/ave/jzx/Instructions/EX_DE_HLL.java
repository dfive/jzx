package pt.ist.ave.jzx.Instructions;

public class EX_DE_HLL extends Instruction {

	public EX_DE_HLL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work16;
		work16 = _cpu.de16();
		_cpu.de16(_cpu.hl16());
		_cpu.hl16(work16);
		
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
