package pt.ist.ave.jzx.Instructions;

public class EXX extends Instruction {

	public EXX(short opCode) {
		super(opCode);
	}
	@Override
	public void execute() {
		int work16 = _cpu.bc16();
		_cpu.bc16(_cpu.getM_bc16alt());
		_cpu.setM_bc16alt(work16);
		
		work16 = _cpu.de16();
		_cpu.de16(_cpu.getM_de16alt());
		_cpu.setM_de16alt(work16);
		
		work16 = _cpu.hl16();
		_cpu.hl16(_cpu.getM_hl16alt());
		_cpu.setM_hl16alt(work16);
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
